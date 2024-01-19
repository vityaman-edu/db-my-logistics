package ru.vityaman.mylogistics.data.jdbc.row

import ru.vityaman.mylogistics.logic.model.Amount
import ru.vityaman.mylogistics.logic.model.DetailedTransaction
import ru.vityaman.mylogistics.logic.model.ItemKind
import ru.vityaman.mylogistics.logic.model.Storage

import java.sql.Timestamp

final case class DetailedTransactionRow(
    moment: Timestamp,
    storageId: Int,
    storageName: String,
    itemKindId: Int,
    itemKindName: String,
    itemKindUnit: String,
    amount: Int
) {
  def asModel: DetailedTransaction =
    DetailedTransaction(
      moment = moment.toInstant,
      storage = Storage.Brief(
        id = storageId,
        name = storageName
      ),
      itemKind = ItemKind(
        id = itemKindId,
        name = itemKindName,
        unit = itemKindUnit
      ),
      amount = Amount(amount)
    )
}
