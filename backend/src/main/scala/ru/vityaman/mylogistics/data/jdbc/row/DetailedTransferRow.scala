package ru.vityaman.mylogistics.data.jdbc.row

import java.sql.Timestamp

final case class DetailedTransferRow(
    transferId: Int,
    withdrawMoment: Timestamp,
    incomeMoment: Timestamp,
    sourceId: Int,
    sourceName: String,
    targetId: Int,
    targetName: String,
    itemKindId: Int,
    itemKindName: String,
    itemKindUnit: String,
    amount: Int
)
