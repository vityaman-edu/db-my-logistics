package ru.vityaman.mylogistics

import ru.vityaman.mylogistics.model.Transfer.{Draft => TransferDraft}
import ru.vityaman.mylogistics.model._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import org.scalajs.dom

import upickle.default._

object API {
  private val base = "http://localhost:8080/api"

  object User {
    def getAll(): Future[List[User]] =
      dom.ext.Ajax
        .get(s"${base}/user")
        .map(xhr => read[List[User]](xhr.responseText))
  }

  object Transaction {
    def getAll(): Future[List[Transaction]] =
      dom.ext.Ajax
        .get(s"${base}/transaction")
        .map(xhr => read[List[Transaction]](xhr.responseText))
  }

  object Transfer {
    def getAll(): Future[List[Transfer]] =
      dom.ext.Ajax
        .get(s"${base}/transfer")
        .map(xhr => read[List[Transfer]](xhr.responseText))

    def create(transfer: TransferDraft): Future[Int] =
      dom.ext.Ajax
        .post(
          url = s"${base}/transfer",
          data = write[TransferDraft](transfer)
        )
        .map(xhr => read[Int](xhr.responseText))

    def addAtom(transferId: Int, atom: Atom): Future[Unit] =
      dom.ext.Ajax
        .post(
          url = s"${base}/transfer/${transferId}/atom",
          data = write[Atom](atom)
        )
        .map(_ => ())

    def approve(transferId: Int): Future[Unit] =
      dom.ext.Ajax
        .post(s"${base}/transfer/${transferId}/approval")
        .map(_ => ())
  }

  object Storage {
    def getAll(): Future[List[StorageDetailed]] =
      dom.ext.Ajax
        .get(s"${base}/storage")
        .map(xhr => read[List[StorageDetailed]](xhr.responseText))

    def getCapacityFree(id: Int): Future[List[Cell]] =
      dom.ext.Ajax
        .get(s"${base}/storage/${id}/capacity/free")
        .map(xhr => read[List[Cell]](xhr.responseText))

    def getCapacityTotal(id: Int): Future[List[Cell]] =
      dom.ext.Ajax
        .get(s"${base}/storage/${id}/capacity/total")
        .map(xhr => read[List[Cell]](xhr.responseText))

    def getBalance(id: Int): Future[List[Pack]] =
      dom.ext.Ajax
        .get(s"${base}/storage/${id}/balance")
        .map(xhr => read[List[Pack]](xhr.responseText))
  }

  object ItemKind {
    def getAll(): Future[List[ItemKind]] =
      dom.ext.Ajax
        .get(s"${base}/item/kind")
        .map(xhr => read[List[ItemKind]](xhr.responseText))
  }
}
