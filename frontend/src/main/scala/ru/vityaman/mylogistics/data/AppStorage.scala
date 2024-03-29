package ru.vityaman.mylogistics.data

import ru.vityaman.mylogistics.model._

import com.raquo.laminar.api.L._

class AppStorage(
    val users: Var[List[User]] = Var(List[User]()),
    val transactions: Var[List[Transaction]] = Var(List[Transaction]()),
    val storages: Var[List[StorageDetailed]] = Var(List[StorageDetailed]()),
    val transfers: Var[List[Transfer]] = Var(List[Transfer]()),
    val itemKinds: Var[List[ItemKind]] = Var(List[ItemKind]()),
    val userId: Var[Int] = Var(1)
) {}
