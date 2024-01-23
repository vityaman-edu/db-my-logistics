package ru.vityaman.mylogistics.logic.service.logged

import zio.RLayer
import zio.Task
import zio.ZIO
import zio.ZLayer

import ru.vityaman.mylogistics.logic.model.Atom
import ru.vityaman.mylogistics.logic.model.DetailedTransaction
import ru.vityaman.mylogistics.logic.model.Transfer
import ru.vityaman.mylogistics.logic.service.TransactionService

final class LoggedTransactionService(origin: TransactionService)
    extends TransactionService {

  override def getAll(): Task[List[DetailedTransaction]] =
    origin.getAll()

  override def getTransfers(): Task[List[Transfer.Equipped]] =
    origin
      .getTransfers()
      .tapErrorCause(ZIO.logCause(_))

  override def create(transfer: Transfer.Request): Task[Transfer.Id] =
    origin
      .create(transfer)
      .tapErrorCause(ZIO.logCause(_))

  override def addAtom(id: Transfer.Id, atom: Atom): Task[Unit] =
    origin
      .addAtom(id, atom)
      .tapErrorCause(ZIO.logCause(_))

  override def approve(id: Transfer.Id): Task[Unit] =
    origin
      .approve(id)
      .tapErrorCause(ZIO.logCause(_))
}

object LoggedTransactionService {
  val layer: RLayer[TransactionService, TransactionService] =
    ZLayer.fromFunction(new LoggedTransactionService(_))
}
