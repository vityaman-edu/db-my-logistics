package ru.vityaman.mylogistics.api.http

import zio._
import zio.http._
import zio.json._

import ru.vityaman.mylogistics.api.http.view.UserView
import ru.vityaman.mylogistics.api.http.view.UserView.encoder
import ru.vityaman.mylogistics.logic.service.UserService

class UserApi(service: UserService) {
  def routes: Routes[Any, Response] = Routes(
    Method.GET / "api" / "user" ->
      handler(
        service
          .getAll()
          .map(_.map(UserView.fromModel))
          .map(_.toJsonPretty)
          .mapBoth(Response.fromThrowable, Response.json)
      )
  )
}

object UserApi {
  val layer: RLayer[UserService, UserApi] =
    ZLayer.fromFunction(new UserApi(_))
}
