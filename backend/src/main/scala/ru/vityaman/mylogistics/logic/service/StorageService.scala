package ru.vityaman.mylogistics.logic.service

import zio.Task

import ru.vityaman.mylogistics.logic.model.Atom
import ru.vityaman.mylogistics.logic.model.Cell
import ru.vityaman.mylogistics.logic.model.Pack
import ru.vityaman.mylogistics.logic.model.Storage
import ru.vityaman.mylogistics.logic.model.User

trait StorageService {
  def getAll(): Task[List[Storage.Detailed]]
  def getCapacityTotal(id: Storage.Id): Task[Cell.Set]
  def getCapacityFree(id: Storage.Id): Task[Cell.Set]
  def getBalance(id: Storage.Id): Task[Pack.Set]
  def addCell(id: Storage.Id, atom: Atom): Task[Unit]
  def assignAdmin(id: User.Id, storageId: Storage.Id): Task[Unit]
}
