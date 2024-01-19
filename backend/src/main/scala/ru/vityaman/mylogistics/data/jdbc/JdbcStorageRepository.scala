package ru.vityaman.mylogistics.data.jdbc

import zio.RLayer
import zio.Task
import zio.ZLayer
import zio.interop.catz._

import ru.vityaman.mylogistics.data.StorageRepository
import ru.vityaman.mylogistics.data.jdbc.row.DetailedStorageRow
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
    ORDER BY storage.name
    """
      .query[DetailedStorageRow]
      .stream
      .transact(xa)
      .compile
      .toList
      .map(_.map(_.asModel))
}

object JdbcStorageRepository {
  val layer: RLayer[Transactor[Task], StorageRepository] =
    ZLayer.fromFunction(new JdbcStorageRepository(_))
}
