package ru.vityaman.mylogistics.api.http.view

import zio.json._

import ru.vityaman.mylogistics.logic.model.ItemKind

final case class ItemKindView(
    id: Int,
    name: String,
    unit: String
)

object ItemKindView {
  def fromModel(model: ItemKind): ItemKindView =
    ItemKindView(
      id = model.id,
      name = model.name,
      unit = model.unit
    )

  implicit val decoder: JsonDecoder[ItemKindView] =
    DeriveJsonDecoder.gen[ItemKindView]
  implicit val encoder: JsonEncoder[ItemKindView] =
    DeriveJsonEncoder.gen[ItemKindView]
}
