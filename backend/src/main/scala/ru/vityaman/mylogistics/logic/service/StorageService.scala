package ru.vityaman.mylogistics.logic.service

import zio.Task

import ru.vityaman.mylogistics.logic.model.Storage

trait StorageService {
  def getAll(): Task[List[Storage.Detailed]]
}
