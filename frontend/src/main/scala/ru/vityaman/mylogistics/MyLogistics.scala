package ru.vityaman.mylogistics

import ru.vityaman.mylogistics.data.AppStorage
import ru.vityaman.mylogistics.view._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util._

import com.raquo.laminar.api.L._
import org.scalajs.dom

object MyLogistics {
  def main(args: Array[String]): Unit = {
    val storage = new AppStorage()

    API.User.getAll().onComplete {
      case Failure(exception) => ???
      case Success(list)      => storage.users.update(_ => list)
    }

    API.Transaction.getAll().onComplete {
      case Failure(exception) => ???
      case Success(list)      => storage.transactions.update(_ => list)
    }

    render(dom.document.querySelector("#root"), Window(storage))
  }
}
