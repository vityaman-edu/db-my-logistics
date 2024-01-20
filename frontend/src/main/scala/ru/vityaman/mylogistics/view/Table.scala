package ru.vityaman.mylogistics.view

import com.raquo.laminar.api.L._

object Table {
  def apply(headers: Seq[String], body: HtmlElement): HtmlElement =
    div(
      maxHeight := "400px",
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
        border := "1px solid",
        thead(tr(headers.map(th(_)))),
        body
      )
    )
}
