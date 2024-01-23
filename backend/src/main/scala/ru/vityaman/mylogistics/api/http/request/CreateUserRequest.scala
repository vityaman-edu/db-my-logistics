package ru.vityaman.mylogistics.api.http.request

import zio.json.DeriveJsonDecoder
import zio.json.DeriveJsonEncoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder

import ru.vityaman.mylogistics.logic.model.User

final case class CreateUserRequest(
    nickname: String,
    firstName: String,
    lastName: String
) {
  def asModel: User.Draft =
    User.Draft(
      nickname = nickname,
      firstName = firstName,
      lastName = lastName
    )
}

object CreateUserRequest {
  implicit val decoder: JsonDecoder[CreateUserRequest] =
    DeriveJsonDecoder.gen[CreateUserRequest]
  implicit val encoder: JsonEncoder[CreateUserRequest] =
    DeriveJsonEncoder.gen[CreateUserRequest]
}
