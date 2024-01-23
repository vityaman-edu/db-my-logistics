package ru.vityaman.mylogistics.data.jdbc.row

import java.sql.Timestamp

/** @note
  *   `itemKind` and `amount` are optional as can be absent.
  */
final case class DetailedTransferRow(
    transferId: Int,
    withdrawMoment: Timestamp,
    incomeMoment: Timestamp,
    sourceId: Int,
    sourceName: String,
    targetId: Int,
    targetName: String,
    itemKindId: Option[Int],
    itemKindName: Option[String],
    itemKindUnit: Option[String],
    amount: Option[Int],
    isCommited: Boolean
) {
  assert(
    !(
      itemKindId.isDefined ^
        itemKindName.isDefined ^
        itemKindUnit.isDefined ^
        amount.isDefined
    )
  )

  def isEmpty: Boolean = itemKindId.isEmpty
}
