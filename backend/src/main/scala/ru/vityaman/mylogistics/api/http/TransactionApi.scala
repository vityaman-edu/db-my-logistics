package ru.vityaman.mylogistics.api.http

import zio._
import zio.http.Method.{GET, POST}
import zio.http._
import zio.json._

import ru.vityaman.mylogistics.api.http.request.CreateAtomRequest
import ru.vityaman.mylogistics.api.http.request.CreateTransferRequest
import ru.vityaman.mylogistics.api.http.view.DetailedTransactionView
import ru.vityaman.mylogistics.api.http.view.EquippedTransferView
import ru.vityaman.mylogistics.logic.service.TransactionService

import java.lang.IllegalArgumentException

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
      },
    POST / "api" / "transfer" ->
      handler { (request: Request) =>
        request.body.asString
          .map(_.fromJson[CreateTransferRequest])
          .flatMap(ZIO.fromEither(_))
          .mapError(_ => new IllegalArgumentException("invalid"))
          .map(_.asModel)
          .flatMap(service.create(_))
          .mapBoth(Response.fromThrowable(_), id => Response.text(id.toString))
      },
    POST / "api" / "transfer" / int("transferId") / "atom" ->
      handler { (transferId: Int, request: Request) =>
        request.body.asString
          .map(_.fromJson[CreateAtomRequest])
          .flatMap(ZIO.fromEither(_))
          .mapError(_ => new IllegalArgumentException("invalid"))
          .map(_.asModel)
          .flatMap(service.addAtom(transferId, _))
          .mapBoth(Response.fromThrowable(_), _ => Response.ok)
      }
  )
}

object TransactionApi {
  val layer: RLayer[TransactionService, TransactionApi] =
    ZLayer.fromFunction(new TransactionApi(_))
}
