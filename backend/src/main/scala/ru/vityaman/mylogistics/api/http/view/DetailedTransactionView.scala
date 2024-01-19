package ru.vityaman.mylogistics.api.http.view

import zio.json._

import ru.vityaman.mylogistics.logic.model.DetailedTransaction

import java.time.Instant

final case class DetailedTransactionView(
    moment: Instant,
    storage: StorageBriefView,
    itemKind: ItemKindView,
    amount: Int
)

object DetailedTransactionView {
  def fromModel(model: DetailedTransaction): DetailedTransactionView =
    DetailedTransactionView(
      moment = model.moment,
      storage = StorageBriefView.fromModel(model.storage),
      itemKind = ItemKindView.fromModel(model.itemKind),
      amount = model.amount.value
    )

  implicit val decoder: JsonDecoder[DetailedTransactionView] =
    DeriveJsonDecoder.gen[DetailedTransactionView]
  implicit val encoder: JsonEncoder[DetailedTransactionView] =
    DeriveJsonEncoder.gen[DetailedTransactionView]
}
