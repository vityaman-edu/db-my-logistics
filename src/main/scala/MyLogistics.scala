import zio.http.Server
import zio.ZIOAppDefault
import api.http.HttpApi

object MyLogistics extends ZIOAppDefault {
  override val run =
    Server.serve(HttpApi.app).provide(Server.default)
}
