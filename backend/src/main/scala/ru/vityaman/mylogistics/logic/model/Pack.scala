package ru.vityaman.mylogistics.logic.model

final case class Pack(
    itemKind: ItemKind,
    amount: Amount
)

object Pack {
  type Set = List[Pack]
}
