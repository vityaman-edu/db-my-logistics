package ru.vityaman.mylogistics.view

import ru.vityaman.mylogistics.model.User

import com.raquo.laminar.api.L._

object UserList {
  def apply(users: Signal[List[User]]): HtmlElement =
    div(
      height := "100%",
      width := "80%",
      overflow := "scroll",
      marginLeft := "auto",
      marginRight := "auto",
      display := "flex",
      flexDirection := "row",
      justifyContent := "center",
      table(
        overflow := "scroll",
        borderCollapse := "separate",
        borderSpacing := "10px 1px",
        thead(tr(th("Id"), th("Nickname"), th("First Name"), th("Last Name"))),
        tbody(children <-- users.split(_.id)(render))
      )
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
