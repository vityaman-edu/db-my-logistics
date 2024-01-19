package ru.vityaman.mylogistics.data.jdbc

import zio._
import zio.interop.catz._

import ru.vityaman.mylogistics.data.TransactionRepository
import ru.vityaman.mylogistics.data.jdbc.row.DetailedTransactionRow
import ru.vityaman.mylogistics.logic.model.DetailedTransaction

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
}

object JdbcTransactionRepository {
  val layer: RLayer[Transactor[Task], TransactionRepository] =
    ZLayer.fromFunction(new JdbcTransactionRepository(_))
}
