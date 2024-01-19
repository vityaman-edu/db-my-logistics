package ru.vityaman.mylogistics.logic.service.basic

import zio._

import ru.vityaman.mylogistics.data.StorageRepository
import ru.vityaman.mylogistics.logic.model.Storage
import ru.vityaman.mylogistics.logic.service.StorageService

private class BasicStorageService(repository: StorageRepository)
    extends StorageService {
  override def getAll(): Task[List[Storage.Detailed]] =
    repository.getAll()
}

object BasicStorageService {
  val layer: RLayer[StorageRepository, StorageService] =
    ZLayer.fromFunction(new BasicStorageService(_))
}
