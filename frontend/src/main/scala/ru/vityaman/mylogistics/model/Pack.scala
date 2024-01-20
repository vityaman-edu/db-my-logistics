package ru.vityaman.mylogistics.model

import upickle.default._

final case class Pack(
    itemKind: ItemKind,
    amount: Int
)

object Pack {
  implicit val rw: ReadWriter[Pack] = macroRW[Pack]
}
