package ru.vityaman.mylogistics.api.http.view

import zio.json._

import ru.vityaman.mylogistics.logic.model.Cell

final case class CellView(
    itemKind: ItemKindView,
    capacity: Int
)

object CellView {
  def fromModel(model: Cell): CellView =
    CellView(
      itemKind = ItemKindView.fromModel(model.itemKind),
      capacity = model.capacity.value
    )

  implicit val decoder: JsonDecoder[CellView] =
    DeriveJsonDecoder.gen[CellView]
  implicit val encoder: JsonEncoder[CellView] =
    DeriveJsonEncoder.gen[CellView]
}
