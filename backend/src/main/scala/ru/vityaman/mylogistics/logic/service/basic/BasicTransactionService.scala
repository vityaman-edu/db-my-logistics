package ru.vityaman.mylogistics.logic.service.basic

import zio._

import ru.vityaman.mylogistics.data.TransactionRepository
import ru.vityaman.mylogistics.logic.model.DetailedTransaction
import ru.vityaman.mylogistics.logic.model.Transfer
import ru.vityaman.mylogistics.logic.service.TransactionService

class BasicTransactionService(repository: TransactionRepository)
    extends TransactionService {
  override def getAll(): Task[List[DetailedTransaction]] =
    repository.getAll()

  override def getTransfers(): Task[List[Transfer.Equipped]] =
    repository.getTransfers()
}

object BasicTransactionService {
  val layer: RLayer[TransactionRepository, TransactionService] =
    ZLayer.fromFunction(new BasicTransactionService(_))
}
