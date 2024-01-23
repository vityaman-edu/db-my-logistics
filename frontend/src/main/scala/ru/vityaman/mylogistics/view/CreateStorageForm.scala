package ru.vityaman.mylogistics.view

import ru.vityaman.mylogistics.API

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util._

import com.raquo.laminar.api.L._
import org.scalajs.dom

object CreateStorageForm {
  def apply(): HtmlElement = {
    val name: Var[String] = Var("")

    val create = () => {
      API.Storage
        .create(name.now())
        .onComplete {
          case Failure(_) => dom.window.alert("Sad things happened... :.)")
          case Success(_) => dom.window.alert("Created, yoyoyo!")
        }
    }

    div(
      display.flex,
      flexDirection.row,
      justifyContent.center,
      div(
        margin.percent(1),
        a("Name: "),
        input(onInput.mapToValue --> name)
      ),
      div(
        margin.percent(1),
        button(typ("button"), "Create", onClick --> (_ => create()))
      )
    )
  }
}
