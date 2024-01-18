package ru.edu.vityaman.mylogistics.logic.model

final case class ItemKind(
    id: ItemKind.Id,
    name: ItemKind.Name,
    unit: ItemKind.Unit
)

object ItemKind {
  type Id = Int
  type Name = String
  type Unit = String
}
