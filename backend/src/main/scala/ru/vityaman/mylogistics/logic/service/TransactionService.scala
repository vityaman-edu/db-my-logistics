package ru.vityaman.mylogistics.logic.service

import zio.Task

import ru.vityaman.mylogistics.logic.model.DetailedTransaction
import ru.vityaman.mylogistics.logic.model.Transfer

trait TransactionService {
  def getAll(): Task[List[DetailedTransaction]]
  def getTransfers(): Task[List[Transfer.Equipped]]
}
