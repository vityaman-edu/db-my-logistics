package ru.vityaman.mylogistics.api.http

import zio._
import zio.http._
import zio.json._

import ru.vityaman.mylogistics.api.http.view.DetailedStorageView
import ru.vityaman.mylogistics.logic.service.StorageService

class StorageApi(service: StorageService) {
  def routes: Routes[Any, Nothing] = Routes(
    Method.GET / "api" / "storage" -> handler(
      service
        .getAll()
        .map(_.map(DetailedStorageView.fromModel))
        .map(_.toJsonPretty)
        .mapBoth(Response.fromThrowable(_), Response.json(_))
    )
  )
}

object StorageApi {
  val layer: RLayer[StorageService, StorageApi] =
    ZLayer.fromFunction(new StorageApi(_))
}
