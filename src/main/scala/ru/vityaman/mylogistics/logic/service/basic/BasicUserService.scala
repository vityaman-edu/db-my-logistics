package ru.vityaman.mylogistics.logic.service.basic

import zio.RLayer
import zio.Task
import zio.ZLayer

import ru.vityaman.mylogistics.data.UserRepository
import ru.vityaman.mylogistics.logic.model.User
import ru.vityaman.mylogistics.logic.service.UserService

class BasicUserService(repository: UserRepository) extends UserService {
  override def register(user: User.Draft): Task[User.Id] =
    repository.register(user)

  override def getById(id: User.Id): Task[User] =
    repository.getById(id)

  override def getAll(): Task[List[User]] =
    repository.getAll()
}

object BasicUserService {
  val layer: RLayer[UserRepository, UserService] =
    ZLayer.fromFunction(new BasicUserService(_))
}
