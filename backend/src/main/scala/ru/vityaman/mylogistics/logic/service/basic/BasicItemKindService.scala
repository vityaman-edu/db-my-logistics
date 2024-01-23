package ru.vityaman.mylogistics.logic.service.basic

import zio.RLayer
import zio.Task
import zio.ZLayer

import ru.vityaman.mylogistics.data.ItemKindRepository
import ru.vityaman.mylogistics.logic.model.ItemKind
import ru.vityaman.mylogistics.logic.service.ItemKindService

final class BasicItemKindService(repository: ItemKindRepository)
    extends ItemKindService {
  override def getAll(): Task[List[ItemKind]] =
    repository.getAll()
}

object BasicItemKindService {
  val layer: RLayer[ItemKindRepository, ItemKindService] =
    ZLayer.fromFunction(new BasicItemKindService(_))
}
