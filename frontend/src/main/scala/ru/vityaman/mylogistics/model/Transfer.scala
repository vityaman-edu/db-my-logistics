package ru.vityaman.mylogistics.model

import upickle.default._

final case class Transfer(
    id: Int,
    source: StorageBrief,
    target: StorageBrief,
    withdrawMoment: String,
    incomeMoment: String,
    packs: List[Pack]
)

object Transfer {
  implicit val rw: ReadWriter[Transfer] = macroRW[Transfer]
}
