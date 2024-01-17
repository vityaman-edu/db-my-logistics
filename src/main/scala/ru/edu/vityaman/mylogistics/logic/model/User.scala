package ru.edu.vityaman.mylogistics.logic.model

import zio.json._

import java.time.Instant

case class User(
    id: User.Id,
    nickname: User.Nickname,
    firstName: User.PersonName,
    lastName: User.PersonName,
    creationMoment: Instant
)

object User {
  type Id = Int
  type Nickname = String
  type PersonName = String

  case class Draft(
      nickname: User.Nickname,
      firstName: User.PersonName,
      lastName: User.PersonName
  )
}
