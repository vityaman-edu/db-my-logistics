package ru.vityaman.mylogistics.api.http.view

import zio.json._

import ru.vityaman.mylogistics.logic.model.Transfer

import java.time.Instant

final case class EquippedTransferView(
    id: Int,
    source: StorageBriefView,
    target: StorageBriefView,
    withdrawMoment: Instant,
    incomeMoment: Instant,
    packs: List[PackView],
    isCommited: Boolean
)

object EquippedTransferView {
  def fromModel(model: Transfer.Equipped): EquippedTransferView =
    EquippedTransferView(
      id = model.info.id,
      source = StorageBriefView.fromModel(model.info.source),
      target = StorageBriefView.fromModel(model.info.target),
      withdrawMoment = model.info.withdrawMoment,
      incomeMoment = model.info.incomeMoment,
      packs = model.packs.map(PackView.fromModel(_)),
      isCommited = model.info.isCommited
    )

  implicit val decoder: JsonDecoder[EquippedTransferView] =
    DeriveJsonDecoder.gen[EquippedTransferView]
  implicit val encoder: JsonEncoder[EquippedTransferView] =
    DeriveJsonEncoder.gen[EquippedTransferView]
}
