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
  final case class Draft(
      sourceId: Int,
      targetId: Int,
      withdrawMoment: String,
      incomeMoment: String
  )

  object Draft {
    implicit val rw: ReadWriter[Transfer.Draft] = macroRW[Transfer.Draft]
  }

  implicit val rw: ReadWriter[Transfer] = macroRW[Transfer]
}
