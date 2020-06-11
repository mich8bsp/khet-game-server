package com.github.mich8bsp

trait Move {
  def reverse(): Move

}

case class RotationMove( pos: BoardPos,  direction: ERotationDirection) extends Move {
  override def reverse(): Move = RotationMove(pos.reverse(), direction)
}
case class PositionMove( from: BoardPos,  to: BoardPos) extends Move {
  override def reverse(): Move = PositionMove(from.reverse(), to.reverse())
}
case class SwitchMove( pos1: BoardPos,  pos2: BoardPos) extends Move {
  override def reverse(): Move = SwitchMove(pos1.reverse(), pos2.reverse())
}

case class BoardPos(i: Int, j: Int){
  def reverse(): BoardPos = BoardPos(GameConfig.COLS-i, GameConfig.ROWS-j)
}