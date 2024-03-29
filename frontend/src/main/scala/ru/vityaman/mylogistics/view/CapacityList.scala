package ru.vityaman.mylogistics.view

import ru.vityaman.mylogistics.API
import ru.vityaman.mylogistics.model.Cell

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util._

import com.raquo.laminar.api.L._

object CapacityList {
  def apply(): HtmlElement = {
    val items: Var[List[Cell]] = Var(List[Cell]())
    val mode: Var[String] = Var("T")
    val storageId: Var[String] = Var("")

    val get = () => {
      (
        mode.now() match {
          case "T" => API.Storage.getCapacityTotal(_)
          case "F" => API.Storage.getCapacityFree(_)
          case "B" => { (id: Int) =>
            API.Storage
              .getBalance(id)
              .map(
                _.map((x) =>
                  Cell(
                    itemKind = x.itemKind,
                    capacity = x.amount
                  )
                )
              )
          }
          case _ => throw new Exception("no")
        }
      )(storageId.now().toInt).onComplete {
        case Failure(exception) => ???
        case Success(list)      => items.update(_ => list)
      }
    }

    div(
      div(
        display.flex,
        flexDirection.row,
        justifyContent.center,
        div(
          margin.percent(1),
          a("Storage: "),
          input(
            controlled(
              value <-- storageId,
              onInput.mapToValue.filter(
                _.forall(Character.isDigit)
              ) --> storageId
            )
          )
        ),
        div(
          margin.percent(1),
          a("T | F | B : "),
          input(
            controlled(
              value <-- mode,
              onInput.mapToValue.filter(
                Seq("F", "T", "B", "").contains(_)
              ) --> mode
            )
          )
        ),
        div(
          margin.percent(1),
          button(typ("button"), "Get", onClick --> (_ => get()))
        )
      ),
      Table(
        Seq("Item", "Count", "Unit"),
        tbody(children <-- items.signal.map(_.map(render)))
      )
    )
  }

  private def render(cell: Cell): HtmlElement =
    tr(
      padding.percent(0),
      margin.percent(0),
      td(cell.itemKind.name),
      td(cell.capacity),
      td(cell.itemKind.unit)
    )
}
