package ru.vityaman.mylogistics.logic.model

final case class Atom(
    itemKind: ItemKind.Id,
    amount: Amount
)
