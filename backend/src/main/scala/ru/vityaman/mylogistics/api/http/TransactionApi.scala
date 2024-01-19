package ru.vityaman.mylogistics.api.http

import zio._
import zio.http._
import zio.json._

import ru.vityaman.mylogistics.api.http.view.DetailedTransactionView
import ru.vityaman.mylogistics.logic.service.TransactionService

class TransactionApi(service: TransactionService) {
  def routes: Routes[Any, Response] = Routes(
    Method.GET / "api" / "transaction" -> handler(
      service
        .getAll()
        .map(_.map(DetailedTransactionView.fromModel))
        .map(_.toJsonPretty)
        .mapBoth(Response.fromThrowable, Response.json)
    )
  )
}

object TransactionApi {
  val layer: RLayer[TransactionService, TransactionApi] =
    ZLayer.fromFunction(new TransactionApi(_))
}
