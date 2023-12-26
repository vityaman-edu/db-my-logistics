package ru.edu.vityaman.mylogistics

import zio.{ULayer, ZLayer}

case class Configuration(
    database: Configuration.Database
)

object Configuration {
  case class Database(
      driver: String,
      url: String,
      user: String,
      password: String
  )

  val layer: ULayer[Configuration] = ZLayer.succeed(
    Configuration(database =
      Database(
        driver = "org.postgresql.Driver",
        url = "jdbc:postgresql://localhost:5432/postgres",
        user = "postgres",
        password = "postgres"
      )
    )
  )
}
