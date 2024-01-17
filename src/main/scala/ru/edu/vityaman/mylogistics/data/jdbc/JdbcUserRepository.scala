package ru.edu.vityaman.mylogistics.data.jdbc

import zio.interop.catz._
import zio.{RLayer, Task, ZLayer}

import doobie.Transactor
import doobie.implicits._
import doobie.implicits.javasql._
import doobie.implicits.javatimedrivernative._
import doobie.refined.implicits._

import ru.edu.vityaman.mylogistics.data.UserRepository
import ru.edu.vityaman.mylogistics.data.jdbc.row.UserRow
import ru.edu.vityaman.mylogistics.logic.model.User

private class JdbcUserRepository(xa: Transactor[Task]) extends UserRepository {
  override def register(user: User.Draft): Task[User.Id] =
    sql"""
    SELECT user_create(
      ${user.nickname},
      ${user.firstName},
      ${user.lastName})
    """.query[User.Id].unique.transact(xa)

  override def getById(id: User.Id): Task[User] =
    sql"""
    SELECT
      id,
      nickname,
      first_name,
      last_name,
      creation_moment
    FROM users WHERE users.id = $id
    """
      .query[UserRow]
      .unique
      .transact(xa)
      .map(_.asModel)

  override def getAll(): Task[List[User]] =
    sql"""
    SELECT
      id,
      nickname,
      first_name,
      last_name,
      creation_moment
    FROM users
    """
      .query[UserRow]
      .stream
      .transact(xa)
      .compile
      .toList
      .map(_.map(_.asModel))
}

object JdbcUserRepository {
  val layer: RLayer[Transactor[Task], UserRepository] =
    ZLayer.fromFunction(new JdbcUserRepository(_))
}
