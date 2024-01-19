package ru.vityaman.mylogistics.view

import ru.vityaman.mylogistics.model.Transaction

import com.raquo.laminar.api.L._

object TransactionList {
  def apply(transactions: Signal[List[Transaction]]): HtmlElement =
    Table(
      Seq("Time", "Storage", "Item", "Amount", "Unit"),
      tbody(children <-- transactions.map(_.map(render)))
    )

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
