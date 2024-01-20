package ru.vityaman.mylogistics.data.jdbc.row

import ru.vityaman.mylogistics.logic.model.Capacity
import ru.vityaman.mylogistics.logic.model.Cell
import ru.vityaman.mylogistics.logic.model.ItemKind

final case class DetailedCellRow(
    itemKindId: Int,
    itemKindName: String,
    itemKindUnit: String,
    capacity: Int
) {
  def asModel: Cell =
    Cell(
      itemKind = ItemKind(
        id = itemKindId,
        name = itemKindName,
        unit = itemKindUnit
      ),
      capacity = Capacity(capacity)
    )
}
