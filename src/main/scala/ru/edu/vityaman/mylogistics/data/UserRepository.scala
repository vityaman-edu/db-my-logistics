package ru.edu.vityaman.mylogistics.data

import zio.Task

import ru.edu.vityaman.mylogistics.logic.model.User

trait UserRepository {
  def register(user: User.Draft): Task[User.Id]
  def getById(id: User.Id): Task[User]
  def getAll(): Task[List[User]]
}
