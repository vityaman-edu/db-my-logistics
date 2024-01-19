package ru.vityaman.mylogistics.view

import ru.vityaman.mylogistics.model.User

import com.raquo.laminar.api.L._

object UserList {
  def apply(users: Signal[List[User]]): HtmlElement =
    Table(
      Seq("Id", "Nickname", "First Name", "Last Name"),
      tbody(children <-- users.split(_.id)(render))
    )

  private def render(id: Int, initial: User, signal: Signal[User]) =
    tr(
      padding := "0%",
      margin := "0%",
      td(child.text <-- signal.map(_.id)),
      td(child.text <-- signal.map(_.nickname)),
      td(child.text <-- signal.map(_.firstName)),
      td(child.text <-- signal.map(_.lastName))
    )
}
