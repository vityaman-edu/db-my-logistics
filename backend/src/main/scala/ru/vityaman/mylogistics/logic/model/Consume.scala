package ru.vityaman.mylogistics.logic.model

import java.time.Instant

object Consume {
  final case class Detailed(
      id: Consume.Id,
      source: Storage.Brief,
      moment: Instant
  )

  type Id = Int
}
