package ru.vityaman.mylogistics.data.jdbc

import zio._
import zio.interop.catz._

import ru.vityaman.mylogistics.data.TransactionRepository
import ru.vityaman.mylogistics.data.jdbc.row.DetailedTransactionRow
import ru.vityaman.mylogistics.data.jdbc.row.DetailedTransferRow
import ru.vityaman.mylogistics.logic.model.Amount
import ru.vityaman.mylogistics.logic.model.DetailedTransaction
import ru.vityaman.mylogistics.logic.model.ItemKind
import ru.vityaman.mylogistics.logic.model.Pack
import ru.vityaman.mylogistics.logic.model.Storage
import ru.vityaman.mylogistics.logic.model.Transfer

import doobie.Transactor
import doobie.implicits._
import doobie.implicits.javasql._

private class JdbcTransactionRepository(xa: Transactor[Task])
    extends TransactionRepository {
  override def getAll(): Task[List[DetailedTransaction]] =
    sql"""
    SELECT 
      storage_transaction.moment AS moment,
      storage.id                 AS storage_id,
      storage.name               AS storage_name,
      item_kind.id               AS item_kind_id,
      item_kind.name             AS item_kind_name,
      item_kind.unit             AS item_kind_unit,
      storage_transaction.amount AS amount
    FROM storage_transaction
    JOIN storage ON storage.id = storage_transaction.storage_id
    JOIN item_kind ON item_kind.id = storage_transaction.item_kind_id
    ORDER BY storage_transaction.moment, storage.id, item_kind.id
    """
      .query[DetailedTransactionRow]
      .stream
      .transact(xa)
      .compile
      .toList
      .map(_.map(_.asModel))

  override def getTransfers(): Task[List[Transfer.Equipped]] =
    sql"""
    SELECT 
      transfer.id,
      transfer.withdraw_moment,
      transfer.income_moment,
      source.id,
      source.name,
      target.id,
      target.name,
      item_kind.id,
      item_kind.name,
      item_kind.unit,
      amount
    FROM transfer
    JOIN storage AS source ON source.id = transfer.source_id
    JOIN storage AS target ON target.id = transfer.target_id
    JOIN transfer_atom ON transfer_atom.transfer_id = transfer.id
    JOIN item_kind ON transfer_atom.item_kind_id = item_kind.id
    """
      .query[DetailedTransferRow]
      .stream
      .transact(xa)
      .compile
      .toList
      .map { rows =>
        rows
          .map(row =>
            Transfer.Detailed(
              id = row.transferId,
              source = Storage.Brief(
                id = row.sourceId,
                name = row.sourceName
              ),
              target = Storage.Brief(
                id = row.targetId,
                name = row.targetName
              ),
              withdrawMoment = row.withdrawMoment.toInstant,
              incomeMoment = row.incomeMoment.toInstant
            ) -> Pack(
              itemKind = ItemKind(
                id = row.itemKindId,
                name = row.itemKindName,
                unit = row.itemKindUnit
              ),
              amount = Amount(row.amount)
            )
          )
          .groupMap(_._1)(_._2)
          .toList
          .map { case (info, packs) => Transfer.Equipped(info, packs) }
      }
}

object JdbcTransactionRepository {
  val layer: RLayer[Transactor[Task], TransactionRepository] =
    ZLayer.fromFunction(new JdbcTransactionRepository(_))
}
