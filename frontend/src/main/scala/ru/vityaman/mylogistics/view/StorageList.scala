package ru.vityaman.mylogistics.view

import ru.vityaman.mylogistics.model.StorageDetailed

import com.raquo.laminar.api.L._

import com.raquo.airstream.core.Signal

object StorageList {
  def apply(storages: Signal[List[StorageDetailed]]): HtmlElement =
    Table(
      Seq("Id", "Name", "Location"),
      tbody(children <-- storages.split(_.id)(render))
    )

  private def render(
      id: Int,
      initial: StorageDetailed,
      signal: Signal[StorageDetailed]
  ) =
    tr(
      padding := "0%",
      margin := "0%",
      td(child.text <-- signal.map(_.id)),
      td(child.text <-- signal.map(_.name)),
      td(child.text <-- signal.map(_.locationName))
    )
}
