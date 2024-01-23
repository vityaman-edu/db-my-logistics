package ru.vityaman.mylogistics.logic.model

import java.time.Instant

object Transfer {
  final case class Detailed(
      id: Transfer.Id,
      source: Storage.Brief,
      target: Storage.Brief,
      withdrawMoment: Instant,
      incomeMoment: Instant,
      isCommited: Boolean
  )

  final case class Equipped(
      info: Transfer.Detailed,
      packs: Pack.Set
  )

  final case class Request(
      sourceId: Storage.Id,
      targetId: Storage.Id,
      withdrawMoment: Instant,
      incomeMoment: Instant
  )

  type Id = Int
}
