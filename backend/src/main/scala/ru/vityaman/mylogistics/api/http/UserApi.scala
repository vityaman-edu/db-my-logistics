package ru.vityaman.mylogistics.api.http

import zio._
import zio.http.Method.{GET, POST}
import zio.http._
import zio.json._

import ru.vityaman.mylogistics.api.http.request.CreateUserRequest
import ru.vityaman.mylogistics.api.http.view.UserView
import ru.vityaman.mylogistics.api.http.view.UserView.encoder
import ru.vityaman.mylogistics.logic.service.UserService

class UserApi(service: UserService) {
  def routes: Routes[Any, Response] = Routes(
    GET / "api" / "user" ->
      handler {
        service
          .getAll()
          .map(_.map(UserView.fromModel))
          .map(_.toJsonPretty)
          .mapBoth(Response.fromThrowable, Response.json)
      },
    POST / "api" / "user" ->
      handler { (request: Request) =>
        request.body.asString
          .map(_.fromJson[CreateUserRequest])
          .flatMap(ZIO.fromEither(_))
          .mapError(_ => new IllegalArgumentException("invalid"))
          .map(_.asModel)
          .flatMap(service.register(_))
          .mapBoth(Response.fromThrowable(_), _ => Response.ok)
      }
  )
}

object UserApi {
  val layer: RLayer[UserService, UserApi] =
    ZLayer.fromFunction(new UserApi(_))
}
