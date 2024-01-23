package ru.vityaman.mylogistics.logic.service.basic

import zio._

import ru.vityaman.mylogistics.data.TransactionRepository
import ru.vityaman.mylogistics.logic.model.Atom
import ru.vityaman.mylogistics.logic.model.DetailedTransaction
import ru.vityaman.mylogistics.logic.model.Transfer
import ru.vityaman.mylogistics.logic.service.TransactionService

class BasicTransactionService(repository: TransactionRepository)
    extends TransactionService {
  override def getAll(): Task[List[DetailedTransaction]] =
    repository.getAll()

  override def getTransfers(): Task[List[Transfer.Equipped]] =
    repository.getTransfers()

  override def create(transfer: Transfer.Request): Task[Transfer.Id] =
    repository.create(transfer)

  override def addAtom(id: Transfer.Id, atom: Atom): Task[Unit] =
    repository.addAtom(id, atom)

  override def approve(id: Transfer.Id): Task[Unit] =
    repository.approve(id)
}

object BasicTransactionService {
  val layer: RLayer[TransactionRepository, TransactionService] =
    ZLayer.fromFunction(new BasicTransactionService(_))
}
