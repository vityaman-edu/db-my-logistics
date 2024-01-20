package ru.vityaman.mylogistics.logic.model

final case class Cell(
    itemKind: ItemKind,
    capacity: Capacity
)

object Cell {
  type Set = List[Cell]
}
