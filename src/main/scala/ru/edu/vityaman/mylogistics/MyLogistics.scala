package ru.edu.vityaman.mylogistics

import ru.edu.vityaman.mylogistics.api.http.{HttpApi, ObservabilityApi, UserApi}
import ru.edu.vityaman.mylogistics.data.jdbc.{
  JdbcUserRepository,
  PostgresTransactor
}
import zio._
import zio.http.{HttpApp, Server}
import zio.logging.backend.SLF4J

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
        Scope.default,
        Configuration.layer,
        PostgresTransactor.layer,
        JdbcUserRepository.layer,
        ObservabilityApi.layer,
        UserApi.layer,
        HttpApi.layer
      )
}
