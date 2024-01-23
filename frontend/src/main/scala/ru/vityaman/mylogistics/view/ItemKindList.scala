package ru.vityaman.mylogistics.view

import ru.vityaman.mylogistics.model.ItemKind

import com.raquo.airstream.core.Signal
import com.raquo.laminar.api.L._

object ItemKindList {
  def apply(itemKinds: Signal[List[ItemKind]]): HtmlElement =
    Table(
      Seq("Id", "Name", "Unit"),
      tbody(children <-- itemKinds.split(_.id) { (id, initial, signal) =>
        tr(
          padding := "0%",
          margin := "0%",
          td(child.text <-- signal.map(_.id)),
          td(child.text <-- signal.map(_.name)),
          td(child.text <-- signal.map(_.unit))
        )
      })
    )
}
