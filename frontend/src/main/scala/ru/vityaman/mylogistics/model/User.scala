package ru.vityaman.mylogistics.model

import upickle.default._

case class User(
    id: Int,
    nickname: String,
    firstName: String,
    lastName: String
)

object User {
  implicit val rw: ReadWriter[User] = macroRW[User]
}
