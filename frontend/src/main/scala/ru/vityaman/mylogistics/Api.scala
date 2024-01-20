package ru.vityaman.mylogistics

import ru.vityaman.mylogistics.model._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import org.scalajs.dom

import model.Cell
import upickle.default._

object API {
  private val base = "http://localhost:8080/api"

  object User {
    def getAll(): Future[List[User]] =
      dom.ext.Ajax
        .get(s"${base}/user")
        .map(xhr => read[List[User]](xhr.responseText))
  }

  object Transaction {
    def getAll(): Future[List[Transaction]] =
      dom.ext.Ajax
        .get(s"${base}/transaction")
        .map(xhr => read[List[Transaction]](xhr.responseText))
  }

  object Storage {
    def getAll(): Future[List[StorageDetailed]] =
      dom.ext.Ajax
        .get(s"${base}/storage")
        .map(xhr => read[List[StorageDetailed]](xhr.responseText))

    def getCapacityFree(id: Int): Future[List[Cell]] =
      dom.ext.Ajax
        .get(s"${base}/storage/${id}/capacity/free")
        .map(xhr => read[List[Cell]](xhr.responseText))

    def getCapacityTotal(id: Int): Future[List[Cell]] =
      dom.ext.Ajax
        .get(s"${base}/storage/${id}/capacity/total")
        .map(xhr => read[List[Cell]](xhr.responseText))

    def getBalance(id: Int): Future[List[Pack]] =
      dom.ext.Ajax
        .get(s"${base}/storage/${id}/balance")
        .map(xhr => read[List[Pack]](xhr.responseText))
  }
}
