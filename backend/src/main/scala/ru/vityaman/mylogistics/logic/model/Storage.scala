package ru.vityaman.mylogistics.logic.model

final case class Storage(
    id: Storage.Id,
    name: Storage.Name,
    locationId: Location.Id
)

object Storage {
  type Id = Int
  type Name = String
}
