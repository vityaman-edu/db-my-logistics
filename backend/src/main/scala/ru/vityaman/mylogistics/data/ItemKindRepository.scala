package ru.vityaman.mylogistics.data

import zio.Task

import ru.vityaman.mylogistics.logic.model.ItemKind

trait ItemKindRepository {
  def getAll(): Task[List[ItemKind]]
}
