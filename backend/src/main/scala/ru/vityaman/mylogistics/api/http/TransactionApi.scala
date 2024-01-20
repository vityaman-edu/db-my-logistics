package ru.vityaman.mylogistics.api.http

import zio._
import zio.http.Method.GET
import zio.http._
import zio.json._

import ru.vityaman.mylogistics.api.http.view.DetailedTransactionView
import ru.vityaman.mylogistics.api.http.view.EquippedTransferView
import ru.vityaman.mylogistics.logic.service.TransactionService

class TransactionApi(service: TransactionService) {
  def routes: Routes[Any, Response] = Routes(
    GET / "api" / "transaction" ->
      handler {
        service
          .getAll()
          .map(_.map(DetailedTransactionView.fromModel))
          .map(_.toJsonPretty)
          .mapBoth(Response.fromThrowable, Response.json)
      },
    GET / "api" / "transfer" ->
      handler {
        service
          .getTransfers()
          .map(_.map(EquippedTransferView.fromModel))
          .map(_.toJsonPretty)
          .mapBoth(Response.fromThrowable(_), Response.json(_))
      }
  )
}

object TransactionApi {
  val layer: RLayer[TransactionService, TransactionApi] =
    ZLayer.fromFunction(new TransactionApi(_))
}
