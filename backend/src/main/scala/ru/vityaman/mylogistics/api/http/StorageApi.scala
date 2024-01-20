package ru.vityaman.mylogistics.api.http

import zio._
import zio.http.Method.GET
import zio.http._
import zio.http.codec.PathCodec.int
import zio.json._

import ru.vityaman.mylogistics.api.http.view.CellView
import ru.vityaman.mylogistics.api.http.view.DetailedStorageView
import ru.vityaman.mylogistics.api.http.view.PackView
import ru.vityaman.mylogistics.logic.service.StorageService

class StorageApi(service: StorageService) {
  def routes: Routes[Any, Response] = Routes(
    GET / "api" / "storage" ->
      handler {
        service
          .getAll()
          .map(_.map(DetailedStorageView.fromModel))
          .map(_.toJsonPretty)
          .mapBoth(Response.fromThrowable(_), Response.json(_))
      },
    GET / "api" / "storage" / int("id") / "capacity" / "free" ->
      handler { (id: Int, _: Request) =>
        service
          .getCapacityFree(id)
          .map(_.map(CellView.fromModel))
          .map(_.toJsonPretty)
          .mapBoth(Response.fromThrowable(_), Response.json(_))
      },
    GET / "api" / "storage" / int("id") / "capacity" / "total" ->
      handler { (id: Int, _: Request) =>
        service
          .getCapacityTotal(id)
          .map(_.map(CellView.fromModel))
          .map(_.toJsonPretty)
          .mapBoth(Response.fromThrowable(_), Response.json(_))
      },
    GET / "api" / "storage" / int("id") / "balance" ->
      handler { (id: Int, _: Request) =>
        service
          .getBalance(id)
          .map(_.map(PackView.fromModel))
          .map(_.toJsonPretty)
          .mapBoth(Response.fromThrowable(_), Response.json(_))
      }
  )

}

object StorageApi {
  val layer: RLayer[StorageService, StorageApi] =
    ZLayer.fromFunction(new StorageApi(_))
}
