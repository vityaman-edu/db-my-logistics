package ru.edu.vityaman.mylogistics.api.http

import zio._
import zio.http.{HttpApp, Routes}

object HttpApi {
  val layer: RLayer[UserApi & ObservabilityApi, HttpApp[Any]] = ZLayer.fromZIO {
    for {
      _ <- ZIO.log("Wiring HTTP API...")
      observability <- ZIO.service[ObservabilityApi]
      user <- ZIO.service[UserApi]
      app = (
        Routes.empty
          ++ observability.routes
          ++ user.routes
      ).toHttpApp
    } yield app
  }
}
