package ru.vityaman.mylogistics.logic.model

final case class Location(
    id: Location.Id,
    name: Location.Name
)

object Location {
  type Id = Int
  type Name = String
}
