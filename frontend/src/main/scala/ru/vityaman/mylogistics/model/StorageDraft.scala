package ru.vityaman.mylogistics.model

import upickle.default._

final case class StorageDraft(
    name: String
)

object StorageDraft {
  implicit val rw: ReadWriter[StorageDraft] = macroRW[StorageDraft]
}
