package ru.vityaman.mylogistics.api.http.view

import zio.json._

import ru.vityaman.mylogistics.logic.model.Storage

final case class StorageBriefView(
    id: Int,
    name: String
)

object StorageBriefView {
  def fromModel(model: Storage.Brief): StorageBriefView =
    StorageBriefView(
      id = model.id,
      name = model.name
    )

  implicit val decoder: JsonDecoder[StorageBriefView] =
    DeriveJsonDecoder.gen[StorageBriefView]
  implicit val encoder: JsonEncoder[StorageBriefView] =
    DeriveJsonEncoder.gen[StorageBriefView]
}
