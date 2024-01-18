package ru.vityaman.mylogistics.logic.model

import java.time.Instant

final case class User(
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
