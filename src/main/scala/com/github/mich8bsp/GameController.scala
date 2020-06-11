package com.github.mich8bsp

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class GameController extends Controller{

  println("game controller created")

  get("/server/status"){ request: Request =>
    "ok"
  }

  post("/game/join"){
    request: Request => GameManager.joinGame
  }

  get("/game/clear"){
    request: Request => GameManager.resetAllGames
  }

}