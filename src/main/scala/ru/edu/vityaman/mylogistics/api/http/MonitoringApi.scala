package ru.edu.vityaman.mylogistics.api.http

import zio._
import zio.http._

class MonitoringApi {
  def routes: Routes[Any, Nothing] = Routes(
    Method.GET / "api" / "monitoring" / "ping" ->
      handler(ZIO.succeed(Response.text("pong")))
  )
}

object MonitoringApi {
  val layer: ULayer[MonitoringApi] =
    ZLayer.succeed(new MonitoringApi())
}
