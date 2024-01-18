package ru.edu.vityaman.mylogistics.data.jdbc.row

import java.sql.Timestamp

import ru.edu.vityaman.mylogistics.logic.model.User

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
