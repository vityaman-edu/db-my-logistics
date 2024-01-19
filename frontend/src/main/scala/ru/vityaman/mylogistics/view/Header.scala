package ru.vityaman.mylogistics.view

import com.raquo.laminar.api.L._

object Header {
  private val el = Seq(
    display := "inline",
    listStyleType := "none",
    fontSize := "6",
    color := Color.textOnDark,
    marginLeft := "0.5%",
    marginRight := "0.5%"
  )

  def apply(): HtmlElement =
    div(
      div(
        marginTop := "0%",
        padding := "0.1%",
        ul(
          marginLeft := "1%",
          li(el, a(el, href := "https://itmo.ru/", "ITMO University")),
          li(el, a(el, href := "https://github.com/vityaman", "Contact"))
        )
      )
    )
}
