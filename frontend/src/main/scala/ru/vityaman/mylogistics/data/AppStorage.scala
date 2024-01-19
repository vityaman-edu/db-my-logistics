package ru.vityaman.mylogistics.data

import ru.vityaman.mylogistics.model._

import com.raquo.laminar.api.L._

class AppStorage(
    val users: Var[List[User]] = Var(List[User]()),
    val transactions: Var[List[Transaction]] = Var(List[Transaction]())
) {}
