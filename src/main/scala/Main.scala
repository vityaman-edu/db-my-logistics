import zio._
import zio.Console

object MyApp extends ZIOAppDefault {
  def run = for {
      _    <- Console.printLine("Hello! What is your name?")
      name <- Console.readLine
      _    <- Console.printLine(s"Hello, ${name}, welcome to ZIO!")
    } yield ()
}
