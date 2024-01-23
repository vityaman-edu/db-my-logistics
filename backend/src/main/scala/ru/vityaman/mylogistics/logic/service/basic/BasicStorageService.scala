package ru.vityaman.mylogistics.logic.service.basic

import zio._

import ru.vityaman.mylogistics.data.StorageRepository
import ru.vityaman.mylogistics.logic.model.Atom
import ru.vityaman.mylogistics.logic.model.Cell
import ru.vityaman.mylogistics.logic.model.Pack
import ru.vityaman.mylogistics.logic.model.Storage
import ru.vityaman.mylogistics.logic.model.User
import ru.vityaman.mylogistics.logic.service.StorageService

private class BasicStorageService(repository: StorageRepository)
    extends StorageService {
  override def getAll(): Task[List[Storage.Detailed]] =
    repository.getAll()

  override def getCapacityTotal(id: Storage.Id): Task[Cell.Set] =
    repository.getCapacityTotal(id)

  override def getCapacityFree(id: Storage.Id): Task[Cell.Set] =
    repository.getCapacityFree(id)

  override def getBalance(id: Storage.Id): Task[Pack.Set] =
    repository.getBalance(id)

  override def addCell(id: Storage.Id, atom: Atom): Task[Unit] =
    repository.addCell(id, atom)

  override def assignAdmin(id: User.Id, storageId: Storage.Id): Task[Unit] =
    repository.adminAssign(id, storageId)
}

object BasicStorageService {
  val layer: RLayer[StorageRepository, StorageService] =
    ZLayer.fromFunction(new BasicStorageService(_))
}
