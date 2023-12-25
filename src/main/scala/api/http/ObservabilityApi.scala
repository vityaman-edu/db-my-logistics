package api.http

import zio.http.*
import zio.*

object ObservabilityApi {
  val routes = Routes(
    Method.GET / "observability" / "ping" ->
      handler(ZIO.succeed(Response.text("pong")))
  )
}
