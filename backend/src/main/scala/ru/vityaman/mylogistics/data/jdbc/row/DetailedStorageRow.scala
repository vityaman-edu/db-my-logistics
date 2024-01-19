package ru.vityaman.mylogistics.data.jdbc.row

import ru.vityaman.mylogistics.logic.model.Location
import ru.vityaman.mylogistics.logic.model.Storage

final case class DetailedStorageRow(
    id: Int,
    name: String,
    locationId: Int,
    locationName: String
) {
  def asModel: Storage.Detailed =
    Storage.Detailed(
      id = id,
      name = name,
      location = Location(
        id = locationId,
        name = locationName
      )
    )
}
