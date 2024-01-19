package ru.vityaman.mylogistics

import ru.vityaman.mylogistics.model.User
import ru.vityaman.mylogistics.model.User.rw

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import org.scalajs.dom
import upickle.default._

object API {
  private val base = "http://localhost:8080/api"

  object User {
    def getAll(): Future[List[User]] =
      dom.ext.Ajax
        .get(s"${base}/user")
        .map(xhr => read[List[User]](xhr.responseText))
  }
}
