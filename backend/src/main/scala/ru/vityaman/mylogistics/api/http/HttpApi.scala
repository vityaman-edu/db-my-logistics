package ru.vityaman.mylogistics.api.http

import zio._
import zio.http.Middleware.addHeader
import zio.http._

object HttpApi {

  val layer: RLayer[
    UserApi &
      MonitoringApi &
      TransactionApi &
      StorageApi &
      ItemApi,
    HttpApp[Any]
  ] =
    ZLayer.fromZIO {
      for {
        _ <- ZIO.log("Wiring HTTP API...")
        observability <- ZIO.service[MonitoringApi]
        user <- ZIO.service[UserApi]
        transaction <- ZIO.service[TransactionApi]
        storage <- ZIO.service[StorageApi]
        item <- ZIO.service[ItemApi]
        app = ((
          Routes.empty
            ++ observability.routes
            ++ user.routes
            ++ transaction.routes
            ++ storage.routes
            ++ item.routes
        ) @@ addHeader(Header.AccessControlAllowOrigin.All)
          @@ addHeader(Header.AccessControlAllowMethods(Method.GET))).toHttpApp
      } yield app
    }
}
