package ru.vityaman.mylogistics.logic.service

import zio.Task

import ru.vityaman.mylogistics.logic.model.DetailedTransaction
import ru.vityaman.mylogistics.logic.model.Transfer
import ru.vityaman.mylogistics.logic.model.Atom

trait TransactionService {
  def getAll(): Task[List[DetailedTransaction]]
  def getTransfers(): Task[List[Transfer.Equipped]]
  def create(transfer: Transfer.Request): Task[Transfer.Id]
  def addAtom(id: Transfer.Id, atom: Atom): Task[Unit]
}
