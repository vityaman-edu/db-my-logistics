package ru.vityaman.mylogistics.model

import upickle.default._

final case class StorageDetailed(
    id: Int,
    name: String,
    locationName: String
)

object StorageDetailed {
  implicit val rw: ReadWriter[StorageDetailed] = macroRW[StorageDetailed]
}
