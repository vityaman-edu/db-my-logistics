package ru.vityaman.mylogistics.view

import ru.vityaman.mylogistics.data.AppStorage

import com.raquo.laminar.api.L._

object Window {
  def apply(storage: AppStorage): HtmlElement =
    div( // Body
      fontFamily := """
          "Cormorant Garamond", 
          Courier, 
          monospace,
          -apple-system, 
          BlinkMacSystemFont, 
          'Segoe UI', 
          'Roboto', 
          'Oxygen', 
          'Ubuntu', 
          'Cantarell', 
          'Fira Sans', 
          'Droid Sans', 
          'Helvetica Neue',
          sans-serif
        """,
      backgroundColor := Color.backgroundDark,
      color := Color.textOnDark,
      boxSizing := "border-box",
      Header(),
      div( // Main
        textAlign := "center",
        marginLeft := "5%",
        marginRight := "5%",
        backgroundColor := Color.backgroundLight,
        color := Color.textOnLight,
        div( // Title
          div(
            paddingTop := "1%",
            paddingBottom := "1%",
            backgroundColor := Color.primary,
            color := Color.textOnDark,
            h1("Welcome to Your Logistics!")
          )
        ),
        div(
          h2("Users"),
          div(UserList(storage.users.signal))
        ),
        div(
          h2("Storages"),
          div(StorageList(storage.storages.signal))
        ),
        div(
          h2("Transactions"),
          div(TransactionList(storage.transactions.signal))
        )
      ),
      Header()
    )
}
