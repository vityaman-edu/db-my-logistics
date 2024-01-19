package ru.vityaman.mylogistics.data

import zio.Task

import ru.vityaman.mylogistics.logic.model.Storage

trait StorageRepository {
  def getAll(): Task[List[Storage.Detailed]]
}
