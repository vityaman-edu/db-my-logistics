package ru.vityaman.mylogistics.api.http.view

import zio.json._

import ru.vityaman.mylogistics.logic.model.Storage

final case class DetailedStorageView(
    id: Int,
    name: String,
    locationName: String
)

object DetailedStorageView {
  def fromModel(model: Storage.Detailed): DetailedStorageView =
    DetailedStorageView(
      id = model.id,
      name = model.name,
      locationName = model.location.name
    )

  implicit val decoder: JsonDecoder[DetailedStorageView] =
    DeriveJsonDecoder.gen[DetailedStorageView]
  implicit val encoder: JsonEncoder[DetailedStorageView] =
    DeriveJsonEncoder.gen[DetailedStorageView]
}
