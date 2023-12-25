package api.http

object HttpApi {
  val observability =
    ObservabilityApi.routes.toHttpApp

  val app =
    observability
}
