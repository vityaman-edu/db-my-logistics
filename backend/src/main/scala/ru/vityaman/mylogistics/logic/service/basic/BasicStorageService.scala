package ru.vityaman.mylogistics.logic.service.basic

import zio._

import ru.vityaman.mylogistics.data.StorageRepository
import ru.vityaman.mylogistics.logic.model.Cell
import ru.vityaman.mylogistics.logic.model.Storage
import ru.vityaman.mylogistics.logic.service.StorageService

private class BasicStorageService(repository: StorageRepository)
    extends StorageService {
  override def getAll(): Task[List[Storage.Detailed]] =
    repository.getAll()

  override def getCapacityTotal(id: Storage.Id): Task[Cell.Set] =
    repository.getCapacityTotal(id)

  override def getCapacityFree(id: Storage.Id): Task[Cell.Set] =
    repository.getCapacityFree(id)
}

object BasicStorageService {
  val layer: RLayer[StorageRepository, StorageService] =
    ZLayer.fromFunction(new BasicStorageService(_))
}
