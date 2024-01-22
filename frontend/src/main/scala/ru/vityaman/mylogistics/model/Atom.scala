package ru.vityaman.mylogistics.model

import upickle.default._

final case class Atom(
    itemKindId: Int,
    amount: Int
)

object Atom {
  implicit val rw: ReadWriter[Atom] = macroRW[Atom]
}
