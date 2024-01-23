package ru.vityaman.mylogistics.api.http.request

import zio.json.DeriveJsonDecoder
import zio.json.DeriveJsonEncoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder

import ru.vityaman.mylogistics.logic.model.Transfer

import java.time.Instant

final case class CreateTransferRequest(
    sourceId: Int,
    targetId: Int,
    withdrawMoment: Instant,
    incomeMoment: Instant
) {
  def asModel: Transfer.Request =
    Transfer.Request(
      sourceId = sourceId,
      targetId = targetId,
      withdrawMoment = withdrawMoment,
      incomeMoment = incomeMoment
    )
}

object CreateTransferRequest {
  implicit val decoder: JsonDecoder[CreateTransferRequest] =
    DeriveJsonDecoder.gen[CreateTransferRequest]
  implicit val encoder: JsonEncoder[CreateTransferRequest] =
    DeriveJsonEncoder.gen[CreateTransferRequest]
}
