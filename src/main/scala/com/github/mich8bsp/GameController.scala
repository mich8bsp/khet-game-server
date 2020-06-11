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
    request: PositionMove with PlayerId =>
      GameManager.makeMove(request, request.playerId)
  }

  post("/game/make_rot_move"){
    request: RotationMove with PlayerId=>
      GameManager.makeMove(request, request.playerId)
  }

  post("/game/make_switch_move"){
    request: SwitchMove with PlayerId=>
      GameManager.makeMove(request, request.playerId)
  }

  get("/game/get_latest_move"){
    request: GetMoveRequest => {
      GameManager.getLatestMove(UUID.fromString(request.playerId))
        .map(GetMoveResponse)
    }
  }


  get("/game/clear"){
    request: Request => GameManager.resetAllGames
  }

}

case class GetMoveRequest(
                         @QueryParam playerId: String
                         )

case class GetMoveResponse(
                          move: Option[MoveRecord]
                          )