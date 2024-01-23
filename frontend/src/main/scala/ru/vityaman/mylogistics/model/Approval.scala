package ru.vityaman.mylogistics.model

import upickle.default._

final case class Approval(
    userId: Int
)

object Approval {
  implicit val rw: ReadWriter[Approval] = macroRW[Approval]
}
