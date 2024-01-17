package ru.edu.vityaman.mylogistics.data.jdbc

import zio._
import zio.interop.catz._
import zio.{Task, ZIO}

import scala.concurrent.ExecutionContext

import doobie.hikari.HikariTransactor

import ru.edu.vityaman.mylogistics.Configuration

object PostgresTransactor {
  val layer: URLayer[Scope with Configuration, HikariTransactor[Task]] =
    ZLayer {
      ZIO.service[Configuration].flatMap { config =>
        HikariTransactor
          .newHikariTransactor[Task](
            config.database.driver,
            config.database.url,
            config.database.user,
            config.database.password,
            ExecutionContext.global
          )
          .toScopedZIO
          .orDie
      }
    }
}
