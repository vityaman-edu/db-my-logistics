package ru.vityaman.mylogistics.model

import upickle.default._

final case class Cell(
    itemKind: ItemKind,
    capacity: Int
)

object Cell {
  implicit val rw: ReadWriter[Cell] = macroRW[Cell]
}
