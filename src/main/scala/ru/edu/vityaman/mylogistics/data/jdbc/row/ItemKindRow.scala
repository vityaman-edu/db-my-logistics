package ru.edu.vityaman.mylogistics.data.jdbc.row

import ru.edu.vityaman.mylogistics.logic.model.ItemKind

final case class ItemKindRow(
    id: Int,
    name: String,
    unit: String
) {
  def asModel: ItemKind =
    ItemKind(
      id = id,
      name = name,
      unit = unit
    )
}
