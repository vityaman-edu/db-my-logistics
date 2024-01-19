package ru.vityaman.mylogistics.logic.service.basic

import zio._

import ru.vityaman.mylogistics.data.TransactionRepository
import ru.vityaman.mylogistics.logic.model.DetailedTransaction
import ru.vityaman.mylogistics.logic.service.TransactionService

class BasicTransactionService(repository: TransactionRepository)
    extends TransactionService {
  override def getAll(): Task[List[DetailedTransaction]] =
    repository.getAll()
}

object BasicTransactionService {
  val layer: RLayer[TransactionRepository, TransactionService] =
    ZLayer.fromFunction(new BasicTransactionService(_))
}
