package ru.edu.vityaman.mylogistics.data.jdbc.entity

import ru.edu.vityaman.mylogistics.model.User

import java.sql.Timestamp

case class UserEntity(
    id: Int,
    nickname: String,
    firstName: String,
    lastName: String,
    creationMoment: Timestamp
) {
  def asModel: User = User(
    id = id,
    nickname = nickname,
    firstName = firstName,
    lastName = lastName,
    creationMoment = creationMoment.toInstant
  )
}
