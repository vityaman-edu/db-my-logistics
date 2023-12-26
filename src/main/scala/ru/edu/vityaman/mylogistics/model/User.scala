package ru.edu.vityaman.mylogistics.model

import eu.timepit.refined.string.MatchesRegex
import eu.timepit.refined.numeric.Positive
import eu.timepit.refined.api.Refined
import eu.timepit.refined.refineV
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

  implicit val decoder: JsonDecoder[User] = DeriveJsonDecoder.gen[User]
  implicit val encoder: JsonEncoder[User] = DeriveJsonEncoder.gen[User]
}
