package ru.vityaman.mylogistics.logic.model

final case class Storage(
    id: Storage.Id,
    name: Storage.Name,
    locationId: Location.Id
)

object Storage {
  type Id = Int
  type Name = String

  final case class Brief(
      id: Storage.Id,
      name: Storage.Name
  )

  final case class Detailed(
      id: Storage.Id,
      name: Storage.Name,
      location: Location
  )
}
