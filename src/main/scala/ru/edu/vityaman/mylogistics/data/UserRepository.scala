package ru.edu.vityaman.mylogistics.data

import ru.edu.vityaman.mylogistics.model.User.Draft
import ru.edu.vityaman.mylogistics.model.User
import zio.Task

trait UserRepository {
  def register(user: Draft): Task[User.Id]
  def getById(id: User.Id): Task[User]
  def getAll(): Task[List[User]]
}
