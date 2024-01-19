package ru.vityaman.mylogistics.data.jdbc.row

import ru.vityaman.mylogistics.logic.model.User

import java.sql.Timestamp

final case class UserRow(
    id: Int,
    nickname: String,
    firstName: String,
    lastName: String,
    creationMoment: Timestamp
) {
  def asModel: User =
    User(
      id = id,
      nickname = nickname,
      firstName = firstName,
      lastName = lastName,
      creationMoment = creationMoment.toInstant
    )
}
