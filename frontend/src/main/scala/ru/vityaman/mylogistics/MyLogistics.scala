package ru.vityaman.mylogistics

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Failure
import scala.util.Success

import com.raquo.laminar.api.L._
import org.scalajs.dom
import ru.vityaman.mylogistics.model.User
import ru.vityaman.mylogistics.view.UserList

object MyLogistics {
  def main(args: Array[String]): Unit = {
    val users = Var(List[User]())

    API.User.getAll().onComplete {
      case Failure(exception) => ???
      case Success(value)     => users.update(_ => value)
    }

    val root = UserList(users.signal)

    val container = dom.document.querySelector("#root")

    render(container, root)
  }
}
