package ru.vityaman.mylogistics.model

import upickle.default._

final case class UserDraft(
    nickname: String,
    firstName: String,
    lastName: String
)

object UserDraft {
  implicit val rw: ReadWriter[UserDraft] = macroRW[UserDraft]
}
