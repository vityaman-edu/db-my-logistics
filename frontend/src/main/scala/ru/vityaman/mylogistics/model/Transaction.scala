package ru.vityaman.mylogistics.model

import upickle.default._

final case class Transaction(
    moment: String,
    storage: StorageBrief,
    itemKind: ItemKind,
    amount: Int
)

object Transaction {
  implicit val rw: ReadWriter[Transaction] = macroRW[Transaction]
}
