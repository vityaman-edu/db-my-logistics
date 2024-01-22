package ru.vityaman.mylogistics

import zio._
import zio.http.{HttpApp, Server}
import zio.logging.backend.SLF4J

import ru.vityaman.mylogistics.api.http._
import ru.vityaman.mylogistics.data.jdbc._
import ru.vityaman.mylogistics.logic.service.basic._
import ru.vityaman.mylogistics.logic.service.logged.LoggedTransactionService

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
        JdbcTransactionRepository.layer,
        JdbcStorageRepository.layer,

        // Service
        BasicUserService.layer,
        (BasicTransactionService.layer >>> LoggedTransactionService.layer),
        BasicStorageService.layer,

        // API
        MonitoringApi.layer,
        UserApi.layer,
        TransactionApi.layer,
        StorageApi.layer,
        HttpApi.layer
      )
}
