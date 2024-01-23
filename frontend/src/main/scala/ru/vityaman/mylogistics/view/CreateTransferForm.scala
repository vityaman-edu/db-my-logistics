package ru.vityaman.mylogistics.view

import ru.vityaman.mylogistics.API
import ru.vityaman.mylogistics.model.Transfer.{Draft => TransferDraft}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util._

import com.raquo.laminar.api.L._
import org.scalajs.dom

object CreateTransferForm {
  def apply(): HtmlElement = {
    val sourceId: Var[Int] = Var(0)
    val targetId: Var[Int] = Var(0)
    val withdrawalMoment: Var[String] = Var("")
    val incomeMoment: Var[String] = Var("")

    val create = () =>
      API.Transfer
        .create(
          TransferDraft(
            sourceId = sourceId.now(),
            targetId = targetId.now(),
            withdrawMoment = withdrawalMoment.now(),
            incomeMoment = incomeMoment.now()
          )
        )
        .onComplete {
          case Failure(_) => dom.window.alert("Sad things happened... :.)")
          case Success(_) => dom.window.alert("Created, yoyoyo!")
        }

    div(
      display.flex,
      flexDirection.row,
      justifyContent.center,
      div(
        margin.percent(1),
        a("Source Id: "),
        input(
          onInput.mapToValue
            .filter(_.forall(Character.isDigit))
            .map(_.toInt) --> sourceId
        )
      ),
      div(
        margin.percent(1),
        a("Target Id: "),
        input(
          onInput.mapToValue
            .filter(_.forall(Character.isDigit))
            .map(_.toInt) --> targetId
        )
      ),
      div(
        margin.percent(1),
        a("Withdrawal Moment: "),
        input(onInput.mapToValue --> withdrawalMoment)
      ),
      div(
        margin.percent(1),
        a("Income Moment: "),
        input(onInput.mapToValue --> incomeMoment)
      ),
      div(
        margin.percent(1),
        button(typ("button"), "Create", onClick --> (_ => create()))
      )
    )
  }
}
