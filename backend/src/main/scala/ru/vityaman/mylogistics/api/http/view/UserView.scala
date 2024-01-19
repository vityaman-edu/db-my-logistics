package ru.vityaman.mylogistics.api.http.view

import zio.json._

import ru.vityaman.mylogistics.logic.model.User

import java.time.Instant

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
