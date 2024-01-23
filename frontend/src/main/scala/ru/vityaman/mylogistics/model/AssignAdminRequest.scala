package ru.vityaman.mylogistics.model

import upickle.default._

final case class AssignAdminRequest(
    userId: Int
)

object AssignAdminRequest {
  implicit val rw: ReadWriter[AssignAdminRequest] = macroRW[AssignAdminRequest]
}
