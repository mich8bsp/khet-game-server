package com.github.mich8bsp

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class GameController extends Controller{

  println("game controller created")

  post("/make-move/") { request: Move =>
    println(s"got move request $request")
  }

  get("/server/status"){ request: Request =>
    "ok"
  }
}

case class Move(

               )