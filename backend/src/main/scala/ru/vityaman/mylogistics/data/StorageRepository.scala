package ru.vityaman.mylogistics.data

import zio.Task

import ru.vityaman.mylogistics.logic.model.Cell
import ru.vityaman.mylogistics.logic.model.Pack
import ru.vityaman.mylogistics.logic.model.Storage

trait StorageRepository {
  def getAll(): Task[List[Storage.Detailed]]
  def getCapacityTotal(id: Storage.Id): Task[Cell.Set]
  def getCapacityFree(id: Storage.Id): Task[Cell.Set]
  def getBalance(id: Storage.Id): Task[Pack.Set]
}
