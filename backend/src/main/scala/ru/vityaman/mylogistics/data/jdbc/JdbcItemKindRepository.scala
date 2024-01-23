package ru.vityaman.mylogistics.data.jdbc

import zio._
import zio.interop.catz._

import ru.vityaman.mylogistics.data.ItemKindRepository
import ru.vityaman.mylogistics.data.jdbc.row.ItemKindRow
import ru.vityaman.mylogistics.logic.model.ItemKind

import doobie.Transactor
import doobie.implicits._

final class JdbcItemKindRepository(xa: Transactor[Task])
    extends ItemKindRepository {
  override def getAll(): Task[List[ItemKind]] =
    sql"""
    SELECT 
      item_kind.id,
      item_kind.name,
      item_kind.unit
    FROM item_kind
    """
      .query[ItemKindRow]
      .stream
      .transact(xa)
      .compile
      .toList
      .map(_.map(_.asModel))
}

object JdbcItemKindRepository {
  val layer: RLayer[Transactor[Task], ItemKindRepository] =
    ZLayer.fromFunction(new JdbcItemKindRepository(_))
}
