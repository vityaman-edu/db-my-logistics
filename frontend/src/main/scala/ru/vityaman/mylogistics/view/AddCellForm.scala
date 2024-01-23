package ru.vityaman.mylogistics.view

import ru.vityaman.mylogistics.API
import ru.vityaman.mylogistics.model.Atom

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util._

import com.raquo.laminar.api.L._
import org.scalajs.dom

object AddCellForm {
  def apply(): HtmlElement = {
    val storageId: Var[String] = Var("")
    val itemKindId: Var[String] = Var("")
    val amount: Var[String] = Var("")

    val addCell = () => {
      API.Storage
        .addCell(
          storageId.now().toInt,
          Atom(
            itemKindId.now().toInt,
            amount.now().toInt
          )
        )
        .onComplete {
          case Failure(_) => dom.window.alert("Sad things happened... :.)")
          case Success(_) => dom.window.alert("Added, yoyoyo!")
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
        a("Item Kind Id: "),
        input(
          onInput.mapToValue.filter(
            _.forall(Character.isDigit)
          ) --> itemKindId
        )
      ),
      div(
        margin.percent(1),
        a("Amount: "),
        input(
          onInput.mapToValue.filter(
            _.forall(Character.isDigit)
          ) --> amount
        )
      ),
      div(
        margin.percent(1),
        button(typ("button"), "Add", onClick --> (_ => addCell()))
      )
    )
  }
}
