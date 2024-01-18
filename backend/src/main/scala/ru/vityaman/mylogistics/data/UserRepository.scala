package ru.vityaman.mylogistics.data

import zio.Task

import ru.vityaman.mylogistics.logic.model.User

trait UserRepository {
  def register(user: User.Draft): Task[User.Id]
  def getById(id: User.Id): Task[User]
  def getAll(): Task[List[User]]
}
