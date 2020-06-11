package com.github.mich8bsp

import java.util.UUID

import akka.actor.Actor

import scala.collection.mutable
import scala.concurrent.Future

class GameLobbyActor extends Actor{
  private val gameRooms: mutable.Map[UUID, GameRoom] = mutable.Map[UUID, GameRoom]

  override def receive: Receive = {
    case JoinGameRequest =>
      val roomNotFull: Option[GameRoom] = gameRooms.values.find(_.roomState == EGameRoomState.AWAITING_PLAYERS)
      val roomToJoin: GameRoom = roomNotFull.getOrElse(createRoom)
      val newPlayer: Player = roomToJoin.joinAsPlayer
      sender().tell(Future.successful(newPlayer), self)
  }

  private def createRoom: GameRoom = {
    val newRoom = GameRoom()
    gameRooms.put(newRoom.id, newRoom)
    newRoom
  }
}

case class GameRoom(
                   id: UUID = UUID.randomUUID()
                   ){
  var player1: Option[Player] = None
  var player2: Option[Player] = None

  def roomState: EGameRoomState = {
    if(player1.isDefined && player2.isDefined){
      EGameRoomState.GAME_ONGOING
    }else{
      EGameRoomState.AWAITING_PLAYERS
    }
  }

  def joinAsPlayer: Player = (player1, player2) match {
    case (Some(_), Some(_)) => throw new Exception("Can't join a full room")
    case (Some(p1), None) =>
      player2 = Some(Player.create(id, p1.color.other()))
      player2.get
    case (None, Some(p2)) =>
      player1 = Some(Player.create(id, p2.color.other()))
      player1.get
    case (None, None) =>
      player1 = Some(Player.create(id, EPlayerColor.GREY))
      player1.get
  }
}
