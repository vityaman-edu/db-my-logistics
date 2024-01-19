package ru.vityaman.mylogistics.view

import ru.vityaman.mylogistics.model.Transaction

import com.raquo.laminar.api.L._

object TransactionList {
  def apply(transactions: Signal[List[Transaction]]): HtmlElement = {
    val storageContains: Var[String] = Var("")
    val itemKindContains: Var[String] = Var("")

    val filtered = transactions.signal
      .combineWith(storageContains.signal)
      .mapN((list, part) => list.filter(_.storage.name.contains(part)))
      .combineWith(itemKindContains.signal)
      .mapN((list, part) => list.filter(_.itemKind.name.contains(part)))

    div(
      div(
        display.flex,
        flexDirection.row,
        justifyContent.center,
        div(
          margin.percent(1),
          a("Storage: "),
          input(onInput.mapToValue --> storageContains)
        ),
        div(
          margin.percent(1),
          a("Item Kind: "),
          input(onInput.mapToValue --> itemKindContains)
        )
      ),
      Table(
        Seq("Time", "Storage", "Item", "Amount", "Unit"),
        tbody(children <-- filtered.map(_.map(render)))
      )
    )
  }

  private def render(transaction: Transaction): HtmlElement =
    tr(
      padding := "0%",
      margin := "0%",
      td(
        ":[0-9][0-9]Z".r.replaceAllIn(
          transaction.moment
            .replace("-", "/")
            .replace("T", " "),
          ""
        )
      ),
      td(transaction.storage.name),
      td(transaction.itemKind.name),
      td(transaction.amount),
      td(transaction.itemKind.unit)
    )
}
