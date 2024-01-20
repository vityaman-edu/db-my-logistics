package ru.vityaman.mylogistics.logic.model

import java.time.Instant

object Supply {
  final case class Detailed(
      id: Supply.Id,
      target: Storage.Brief,
      moment: Instant
  )

  type Id = Int
}
