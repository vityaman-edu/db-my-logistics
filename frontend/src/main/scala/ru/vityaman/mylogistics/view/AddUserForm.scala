package ru.vityaman.mylogistics.view

import ru.vityaman.mylogistics.API
import ru.vityaman.mylogistics.model.UserDraft

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util._

import com.raquo.laminar.api.L._
import org.scalajs.dom

object AddUserForm {
  def apply(): HtmlElement = {
    val nickname: Var[String] = Var("")
    val firstName: Var[String] = Var("")
    val lastName: Var[String] = Var("")

    val register = () => {
      API.User
        .register(
          UserDraft(
            nickname = nickname.now(),
            firstName = firstName.now(),
            lastName = lastName.now()
          )
        )
        .onComplete {
          case Failure(_) => dom.window.alert("Sad things happened... :.)")
          case Success(_) => dom.window.alert("Registered, yoyoyo!")
        }
    }

    div(
      display.flex,
      flexDirection.row,
      justifyContent.center,
      div(
        margin.percent(1),
        a("Nickname: "),
        input(onInput.mapToValue --> nickname)
      ),
      div(
        margin.percent(1),
        a("First Name: "),
        input(onInput.mapToValue --> firstName)
      ),
      div(
        margin.percent(1),
        a("Last Name: "),
        input(onInput.mapToValue --> lastName)
      ),
      div(
        margin.percent(1),
        button(typ("button"), "Register", onClick --> (_ => register()))
      )
    )
  }
}
