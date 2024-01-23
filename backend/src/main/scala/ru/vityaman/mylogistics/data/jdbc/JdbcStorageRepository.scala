package ru.vityaman.mylogistics.data.jdbc

import zio.RLayer
import zio.Task
import zio.ZLayer
import zio.interop.catz._

import ru.vityaman.mylogistics.data.StorageRepository
import ru.vityaman.mylogistics.data.jdbc.row.DetailedCellRow
import ru.vityaman.mylogistics.data.jdbc.row.DetailedPackRow
import ru.vityaman.mylogistics.data.jdbc.row.DetailedStorageRow
import ru.vityaman.mylogistics.logic.model.Cell
import ru.vityaman.mylogistics.logic.model.Pack
import ru.vityaman.mylogistics.logic.model.Atom
import ru.vityaman.mylogistics.logic.model.Storage

import doobie.Transactor
import doobie.implicits._

private class JdbcStorageRepository(xa: Transactor[Task])
    extends StorageRepository {
  override def getAll(): Task[List[Storage.Detailed]] =
    sql"""
    SELECT 
      storage.id    AS id,
      storage.name  AS name,
      location.id   AS location_id,
      location.name AS location_name
    FROM storage
    JOIN location ON storage.location_id = location.id
    """
      .query[DetailedStorageRow]
      .stream
      .transact(xa)
      .compile
      .toList
      .map(_.map(_.asModel))

  def getCapacityTotal(id: Storage.Id): Task[Cell.Set] =
    sql"""
    SELECT 
      item_kind.id    AS item_kind_id,
      item_kind.name  AS item_kind_name,
      item_kind.unit  AS item_kind_unit,
      capacity        AS capacity
    FROM storage_capacity_total
    JOIN item_kind ON item_kind_id = item_kind.id
    WHERE storage_id = ${id}
    ORDER BY item_kind.id
    """
      .query[DetailedCellRow]
      .stream
      .transact(xa)
      .compile
      .toList
      .map(_.map(_.asModel))

  def getCapacityFree(id: Storage.Id): Task[Cell.Set] =
    sql"""
    SELECT 
      item_kind.id    AS item_kind_id,
      item_kind.name  AS item_kind_name,
      item_kind.unit  AS item_kind_unit,
      capacity        AS capacity
    FROM storage_capacity_free(max_moment()) 
    JOIN item_kind ON item_kind_id = item_kind.id
    WHERE storage_id = ${id}
    ORDER BY item_kind.id
    """
      .query[DetailedCellRow]
      .stream
      .transact(xa)
      .compile
      .toList
      .map(_.map(_.asModel))

  def getBalance(id: Storage.Id): Task[Pack.Set] =
    sql"""
    SELECT 
      item_kind.id    AS item_kind_id,
      item_kind.name  AS item_kind_name,
      item_kind.unit  AS item_kind_unit,
      amount          AS amount
    FROM storage
    JOIN storage_balance(max_moment()) 
      ON storage.id = storage_balance.storage_id
    JOIN item_kind ON storage_balance.item_kind_id = item_kind.id
    WHERE storage.id = ${id}
    ORDER BY item_kind.id
    """
      .query[DetailedPackRow]
      .stream
      .transact(xa)
      .compile
      .toList
      .map(_.map(_.asModel))

  override def addCell(id: Storage.Id, atom: Atom): Task[Unit] =
    sql"""
    SELECT storage_cell_create(
      ${id}, 
      ${atom.itemKind}, 
      ${atom.amount}
    )
    """
      .query[Unit]
      .unique
      .transact(xa)
}

object JdbcStorageRepository {
  val layer: RLayer[Transactor[Task], StorageRepository] =
    ZLayer.fromFunction(new JdbcStorageRepository(_))
}
