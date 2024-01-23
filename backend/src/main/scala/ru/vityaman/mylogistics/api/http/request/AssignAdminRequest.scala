package ru.vityaman.mylogistics.api.http.request

import zio.json.DeriveJsonDecoder
import zio.json.DeriveJsonEncoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder

final case class AssignAdminRequest(
    userId: Int
)

object AssignAdminRequest {
  implicit val decoder: JsonDecoder[AssignAdminRequest] =
    DeriveJsonDecoder.gen[AssignAdminRequest]
  implicit val encoder: JsonEncoder[AssignAdminRequest] =
    DeriveJsonEncoder.gen[AssignAdminRequest]
}
