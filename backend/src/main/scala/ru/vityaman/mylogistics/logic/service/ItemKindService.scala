package ru.vityaman.mylogistics.logic.service

import zio.Task

import ru.vityaman.mylogistics.logic.model.ItemKind

trait ItemKindService {
  def getAll(): Task[List[ItemKind]]
}
