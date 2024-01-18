package ru.vityaman.mylogistics.data.jdbc.row

import ru.vityaman.mylogistics.logic.model.ItemKind

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
