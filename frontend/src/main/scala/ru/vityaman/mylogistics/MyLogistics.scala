package ru.vityaman.mylogistics

import com.raquo.laminar.api.L._
import org.scalajs.dom
import ru.vityaman.mylogistics.model.User
import ru.vityaman.mylogistics.view.UserList

object MyLogistics {
  def main(args: Array[String]): Unit = {
    val users = Var(List[User]())
    users.update(_ :+ User(1, "arkash", "Arkady", "Parovozov"))
    users.update(_ :+ User(2, "validik", "Vladimir", "Ivanov"))
    users.update(_ :+ User(3, "validik", "Vladimir", "Ivanov"))
    users.update(_ :+ User(4, "validik", "Vladimir", "Ivanov"))
    users.update(_ :+ User(5, "validik", "Vladimir", "Ivanov"))
    users.update(_ :+ User(6, "validik", "Vladimir", "Ivanov"))

    val root = UserList(users.signal)

    val container = dom.document.querySelector("#root")

    render(container, root)
  }
}
