package ru.vityaman.mylogistics.logic.model

import java.time.Instant

final case class DetailedTransaction(
    moment: Instant,
    storage: Storage.Brief,
    itemKind: ItemKind,
    amount: Amount
)

object DetailedTransaction {}
