package ru.edu.vityaman.mylogistics.api.http

import ru.edu.vityaman.mylogistics.data.UserRepository
import ru.edu.vityaman.mylogistics.model.User.decoder
import ru.edu.vityaman.mylogistics.model.User.encoder
import zio.http._
import zio.json._
import zio._

class UserApi(repository: UserRepository) {
  def routes: Routes[Any, Response] = Routes(
    Method.POST / "api" / "user" ->
      handler(ZIO.succeed(Response.text("user"))),
    Method.GET / "api" / "user" ->
      handler(
        repository
          .getAll()
          .tapError(error => ZIO.succeed(error.printStackTrace()))
          .map(_.toJsonPretty)
          .mapBoth(
            _ => Response.error(Status.InternalServerError),
            Response.json(_)
          )
          .tap(_ => ZIO.log("GET /api/user"))
      )
  )
}

object UserApi {
  val layer: RLayer[UserRepository, UserApi] =
    ZLayer.fromFunction(new UserApi(_))
}
