package ru.vityaman.mylogistics.model

import upickle.default._

final case class ItemKind(
    id: Int,
    name: String,
    unit: String
)

object ItemKind {
  implicit val rw: ReadWriter[ItemKind] = macroRW[ItemKind]
}
