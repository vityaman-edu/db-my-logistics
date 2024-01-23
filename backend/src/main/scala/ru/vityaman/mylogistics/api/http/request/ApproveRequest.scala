package ru.vityaman.mylogistics.api.http.request

import zio.json.DeriveJsonDecoder
import zio.json.DeriveJsonEncoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder

final case class ApproveRequest(
    userId: Int
)

object ApproveRequest {
  implicit val decoder: JsonDecoder[ApproveRequest] =
    DeriveJsonDecoder.gen[ApproveRequest]
  implicit val encoder: JsonEncoder[ApproveRequest] =
    DeriveJsonEncoder.gen[ApproveRequest]
}
