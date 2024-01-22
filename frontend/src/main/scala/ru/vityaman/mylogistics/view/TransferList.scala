package ru.vityaman.mylogistics.view

import ru.vityaman.mylogistics.model.Pack
import ru.vityaman.mylogistics.model.Transfer

import com.raquo.laminar.api.L._

object TransferList {
  def apply(transfers: Signal[List[Transfer]]): HtmlElement = {
    div(
      Table(
        Seq("Id", "Withdraw", "Income", "Source", "Target", "Items"),
        tbody(children <-- transfers.signal.split(_.id) { (id, init, signal) =>
          tr(
            padding := "0%",
            margin := "0%",
            td(child.text <-- signal.map(_.id)),
            td(child.text <-- signal.map(t => time(t.withdrawMoment))),
            td(child.text <-- signal.map(t => time(t.incomeMoment))),
            td(child.text <-- signal.map(_.source.name)),
            td(child.text <-- signal.map(_.target.name)),
            td(
              width.px(300),
              Table(
                Seq("Item", "Amount", "Unit"),
                tbody(children <-- signal.map(_.packs).split(_.itemKind.id) {
                  (id, init, signal: Signal[Pack]) =>
                    tr(
                      padding := "0%",
                      margin := "0%",
                      td(child.text <-- signal.map(_.itemKind.name)),
                      td(child.text <-- signal.map(_.amount)),
                      td(child.text <-- signal.map(_.itemKind.unit))
                    )
                })
              )
            )
          )
        })
      )
    )
  }

  private def time(string: String): String = {
    var str = string.replace("-", "/").replace("T", " ")
    str = ":[0-9][0-9]Z".r.replaceAllIn(str, "")
    str
  }

}
