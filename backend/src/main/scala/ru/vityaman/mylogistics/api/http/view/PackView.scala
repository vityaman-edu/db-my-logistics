package ru.vityaman.mylogistics.api.http.view

import zio.json._

import ru.vityaman.mylogistics.logic.model.Pack

final case class PackView(
    itemKind: ItemKindView,
    amount: Int
)

object PackView {
  def fromModel(model: Pack): PackView =
    PackView(
      itemKind = ItemKindView.fromModel(model.itemKind),
      amount = model.amount.value
    )

  implicit val decoder: JsonDecoder[PackView] =
    DeriveJsonDecoder.gen[PackView]
  implicit val encoder: JsonEncoder[PackView] =
    DeriveJsonEncoder.gen[PackView]
}
