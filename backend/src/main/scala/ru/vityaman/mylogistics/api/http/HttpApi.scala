package ru.vityaman.mylogistics.api.http

import zio._
import zio.http.Middleware.addHeader
import zio.http._

object HttpApi {
  val layer: RLayer[UserApi & MonitoringApi, HttpApp[Any]] = ZLayer.fromZIO {
    for {
      _ <- ZIO.log("Wiring HTTP API...")
      observability <- ZIO.service[MonitoringApi]
      user <- ZIO.service[UserApi]
      app = ((
        Routes.empty
          ++ observability.routes
          ++ user.routes
      ) @@ addHeader(Header.AccessControlAllowOrigin.All)
        @@ addHeader(Header.AccessControlAllowMethods(Method.GET))).toHttpApp
    } yield app
  }
}
