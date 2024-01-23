package ru.vityaman.mylogistics.api.http.request

import zio.json.DeriveJsonDecoder
import zio.json.DeriveJsonEncoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder

import ru.vityaman.mylogistics.logic.model.Storage

final case class CreateStorageRequest(
    name: String
) {
  def asModel: Storage.Draft =
    Storage.Draft(
      name = name
    )
}

object CreateStorageRequest {
  implicit val decoder: JsonDecoder[CreateStorageRequest] =
    DeriveJsonDecoder.gen[CreateStorageRequest]
  implicit val encoder: JsonEncoder[CreateStorageRequest] =
    DeriveJsonEncoder.gen[CreateStorageRequest]
}
