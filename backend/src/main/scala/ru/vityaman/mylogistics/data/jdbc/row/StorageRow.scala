package ru.vityaman.mylogistics.data.jdbc.row

import ru.vityaman.mylogistics.logic.model.Storage

final case class StorageRow(
    id: Int,
    name: String,
    locationId: Int
) {
  def asModel: Storage =
    Storage(
      id = id,
      name = name,
      locationId = locationId
    )
}
