package ru.vityaman.mylogistics.data.jdbc.row

import ru.vityaman.mylogistics.logic.model.Amount
import ru.vityaman.mylogistics.logic.model.ItemKind
import ru.vityaman.mylogistics.logic.model.Pack

final case class DetailedPackRow(
    itemKindId: Int,
    itemKindName: String,
    itemKindUnit: String,
    amount: Int
) {
  def asModel: Pack =
    Pack(
      itemKind = ItemKind(
        id = itemKindId,
        name = itemKindName,
        unit = itemKindUnit
      ),
      amount = Amount(amount)
    )
}
