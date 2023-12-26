package ru.edu.vityaman.mylogistics.api.http

import zio.http._
import zio._

class ObservabilityApi {
  def routes: Routes[Any, Nothing] = Routes(
    Method.GET / "api" / "observability" / "ping" ->
      handler(ZIO.succeed(Response.text("pong")))
  )
}

object ObservabilityApi {
  val layer: ULayer[ObservabilityApi] =
    ZLayer.succeed(new ObservabilityApi())
}
