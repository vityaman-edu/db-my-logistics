package ru.vityaman.mylogistics.view

import ru.vityaman.mylogistics.API

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util._

import com.raquo.laminar.api.L._
import org.scalajs.dom

object AssignAdminForm {
  def apply(): HtmlElement = {
    val storageId: Var[String] = Var("")
    val userId: Var[String] = Var("")

    val addCell = () => {
      API.Storage
        .assignAdmin(storageId.now().toInt, userId.now().toInt)
        .onComplete {
          case Failure(_) => dom.window.alert("Sad things happened... :.)")
          case Success(_) => dom.window.alert("Assigned, yoyoyo!")
        }
    }

    div(
      display.flex,
      flexDirection.row,
      justifyContent.center,
      div(
        margin.percent(1),
        a("Storage Id: "),
        input(
          onInput.mapToValue.filter(
            _.forall(Character.isDigit)
          ) --> storageId
        )
      ),
      div(
        margin.percent(1),
        a("User Id: "),
        input(
          onInput.mapToValue.filter(
            _.forall(Character.isDigit)
          ) --> userId
        )
      ),
      div(
        margin.percent(1),
        button(typ("button"), "Assign", onClick --> (_ => addCell()))
      )
    )
  }
}
