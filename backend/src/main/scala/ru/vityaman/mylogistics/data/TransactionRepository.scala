package ru.vityaman.mylogistics.data

import zio._

import ru.vityaman.mylogistics.logic.model.DetailedTransaction
import ru.vityaman.mylogistics.logic.model.Transfer

trait TransactionRepository {
  def getAll(): Task[List[DetailedTransaction]]
  def getTransfers(): Task[List[Transfer.Equipped]]
}
