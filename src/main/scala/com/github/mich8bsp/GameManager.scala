package com.github.mich8bsp

import java.util.concurrent.{Executors, TimeUnit}

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Future}

object GameManager  {
  private implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(15))
  private val actorSystem = ActorSystem("khet-game-server")
  private val gameManagerActor = actorSystem.actorOf(Props(new GameManagerActor))
  private implicit val timeout: Timeout = Timeout(Duration(1, TimeUnit.MINUTES))

  def joinGame: Future[Player] = {
    gameManagerActor.ask(JoinGameRequest)
      .mapTo[Player]
  }

  def resetAllGames: Future[Boolean] = {
    gameManagerActor.ask(ClearGamesRequest)
      .mapTo[Boolean]
  }
}

class GameManagerActor extends Actor {
  val gameLobby: ActorRef = context.actorOf(Props(new GameLobbyActor))
  override def receive: Receive = {
    case JoinGameRequest => gameLobby.tell(JoinGameRequest, sender)
    case ClearGamesRequest => gameLobby.tell(ClearGamesRequest, sender)
  }
}

case object JoinGameRequest
case object ClearGamesRequest