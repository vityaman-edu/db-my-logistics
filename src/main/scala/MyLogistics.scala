import zio.http.Server
import zio.*
import api.http.HttpApi

object MyLogistics extends ZIOAppDefault {
  override def run = for {
    _ <- ZIO.log("Starting service MyLogistics...")
    server <- Server
      .serve(HttpApi.app)
      .provide(Server.default)
      .onInterrupt(ZIO.log("Service was shut down."))
  } yield server
}
