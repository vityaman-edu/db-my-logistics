package ru.vityaman.mylogistics.logic.service

import zio.Task

import ru.vityaman.mylogistics.logic.model.DetailedTransaction

trait TransactionService {
  def getAll(): Task[List[DetailedTransaction]]
}
