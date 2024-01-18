package ru.vityaman.mylogistics.logic.service

import zio.Task

import ru.vityaman.mylogistics.logic.model.User

trait UserService {
  def register(user: User.Draft): Task[User.Id]
  def getById(id: User.Id): Task[User]
  def getAll(): Task[List[User]]
}
