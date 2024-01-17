package ru.edu.vityaman.mylogistics

import zio._
import zio.http.{HttpApp, Server}
import zio.logging.backend.SLF4J

import ru.edu.vityaman.mylogistics.api.http.{HttpApi, MonitoringApi, UserApi}
import ru.edu.vityaman.mylogistics.data.jdbc.{
  JdbcUserRepository,
  PostgresTransactor
}
import ru.edu.vityaman.mylogistics.logic.service.basic.BasicUserService

object MyLogistics extends ZIOAppDefault {
  override def run: RIO[ZIOAppArgs & Scope, Nothing] = (for {
    _ <- ZIO.log("Starting service MyLogistics...")
    app <- ZIO.service[HttpApp[Any]]
    server <- Server
      .serve(app)
      .provide(Server.default)
      .onInterrupt(ZIO.log("Service was shut down."))
  } yield server).provide(layer)

  private val layer: RLayer[Any, HttpApp[Any]] =
    (Runtime.removeDefaultLoggers >>> SLF4J.slf4j) >>>
      ZLayer.make[HttpApp[Any]](
        // ZIO
        Scope.default,

        // Preferences
        Configuration.layer,

        // Database
        PostgresTransactor.layer,
        JdbcUserRepository.layer,

        // Service
        BasicUserService.layer,

        // API
        MonitoringApi.layer,
        UserApi.layer,
        HttpApi.layer
      )
}
