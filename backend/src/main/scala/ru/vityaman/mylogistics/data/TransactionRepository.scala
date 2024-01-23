package ru.vityaman.mylogistics.data

import zio._

import ru.vityaman.mylogistics.logic.model.Atom
import ru.vityaman.mylogistics.logic.model.DetailedTransaction
import ru.vityaman.mylogistics.logic.model.Transfer

trait TransactionRepository {
  def getAll(): Task[List[DetailedTransaction]]
  def getTransfers(): Task[List[Transfer.Equipped]]
  def create(transfer: Transfer.Request): Task[Transfer.Id]
  def addAtom(id: Transfer.Id, atom: Atom): Task[Unit]
  def approve(approval: Transfer.Approval): Task[Unit]
}
