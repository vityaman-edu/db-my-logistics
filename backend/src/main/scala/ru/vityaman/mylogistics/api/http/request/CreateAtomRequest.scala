package ru.vityaman.mylogistics.api.http.request

import zio.json.DeriveJsonDecoder
import zio.json.DeriveJsonEncoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder

import ru.vityaman.mylogistics.logic.model.Amount
import ru.vityaman.mylogistics.logic.model.Atom

final case class CreateAtomRequest(
    itemKindId: Int,
    amount: Int
) {
  def asModel: Atom =
    Atom(
      itemKind = itemKindId,
      amount = Amount(amount)
    )
}

object CreateAtomRequest {
  implicit val decoder: JsonDecoder[CreateAtomRequest] =
    DeriveJsonDecoder.gen[CreateAtomRequest]
  implicit val encoder: JsonEncoder[CreateAtomRequest] =
    DeriveJsonEncoder.gen[CreateAtomRequest]
}
