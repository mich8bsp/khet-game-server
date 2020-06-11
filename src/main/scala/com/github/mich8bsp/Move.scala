package com.github.mich8bsp

trait Move

case class RotationMove( pos: BoardPos,  direction: ERotationDirection) extends Move
case class PositionMove( from: BoardPos,  to: BoardPos) extends Move
case class SwitchMove( pos1: BoardPos,  pos2: BoardPos) extends Move

case class BoardPos(i: Int, j: Int)