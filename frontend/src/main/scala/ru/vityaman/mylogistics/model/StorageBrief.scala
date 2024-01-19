package ru.vityaman.mylogistics.model

import upickle.default._

final case class StorageBrief(
    id: Int,
    name: String
)

object StorageBrief {
  implicit val rw: ReadWriter[StorageBrief] = macroRW[StorageBrief]
}
