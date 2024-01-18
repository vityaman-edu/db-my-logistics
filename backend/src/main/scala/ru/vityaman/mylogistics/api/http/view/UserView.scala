package ru.vityaman.mylogistics.api.http.view

import zio.json.DeriveJsonDecoder
import zio.json.DeriveJsonEncoder
import zio.json.JsonDecoder
import zio.json.JsonEncoder

import java.time.Instant

import ru.vityaman.mylogistics.logic.model.User

case class UserView(
    id: Int,
    nickname: String,
    firstName: String,
    lastName: String,
    creationMoment: Instant
)

object UserView {
  def fromModel(model: User): UserView =
    UserView(
      id = model.id,
      nickname = model.nickname,
      firstName = model.firstName,
      lastName = model.lastName,
      creationMoment = model.creationMoment
    )

  implicit val decoder: JsonDecoder[UserView] = DeriveJsonDecoder.gen[UserView]
  implicit val encoder: JsonEncoder[UserView] = DeriveJsonEncoder.gen[UserView]
}
