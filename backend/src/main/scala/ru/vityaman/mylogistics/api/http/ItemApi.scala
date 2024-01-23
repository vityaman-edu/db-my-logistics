package ru.vityaman.mylogistics.api.http

import zio._
import zio.http.Method.GET
import zio.http._
import zio.json._

import ru.vityaman.mylogistics.api.http.view.ItemKindView
import ru.vityaman.mylogistics.logic.service.ItemKindService

class ItemApi(service: ItemKindService) {
  def routes: Routes[Any, Response] = Routes(
    GET / "api" / "item" / "kind" ->
      handler {
        service
          .getAll()
          .map(_.map(ItemKindView.fromModel))
          .map(_.toJsonPretty)
          .mapBoth(Response.fromThrowable(_), Response.json(_))
      }
  )
}

object ItemApi {
  val layer: RLayer[ItemKindService, ItemApi] =
    ZLayer.fromFunction(new ItemApi(_))
}
