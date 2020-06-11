package com.github.mich8bsp

import java.util.UUID

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.http.annotations.QueryParam

class GameController extends Controller{
  import GameManager.ec

  println("game controller created")

  get("/server/status"){ request: Request =>
    "ok"
  }

  post("/game/join"){
    request: Request => GameManager.joinGame
  }

  post("/game/make_pos_move"){
    request: MakePositionMoveRequest =>
      GameManager.makeMove(request.move, request.playerId)
  }

  post("/game/make_rot_move"){
    request: MakeRotationMoveRequest =>
      GameManager.makeMove(request.move, request.playerId)
  }

  post("/game/make_switch_move"){
    request: MakeSwitchMoveRequest=>
      GameManager.makeMove(request.move, request.playerId)
  }

  get("/game/get_latest_move"){
    request: GetMoveRequest => {
      GameManager.getLatestMove(UUID.fromString(request.playerId))
        .map(move => GetMoveResponse(move, move.map(_.move.moveType).getOrElse(EMoveType.NONE)))
    }
  }


  get("/game/clear"){
    request: Request => GameManager.resetAllGames
  }

}

case class MakePositionMoveRequest(
                                  move: PositionMove,
                                  playerId: UUID
                                  )

case class MakeRotationMoveRequest(
                                  move: RotationMove,
                                  playerId: UUID
                                  )
case class MakeSwitchMoveRequest(
                                move: SwitchMove,
                                playerId: UUID
                                )

case class GetMoveRequest(
                         @QueryParam playerId: String
                         )

case class GetMoveResponse(
                          move: Option[MoveRecord],
                          moveType: EMoveType
                          )