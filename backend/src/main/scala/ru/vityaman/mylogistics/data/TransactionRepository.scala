package ru.vityaman.mylogistics.data

import zio._

import ru.vityaman.mylogistics.logic.model.DetailedTransaction

trait TransactionRepository {
  def getAll(): Task[List[DetailedTransaction]]
}
