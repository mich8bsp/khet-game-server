package com.github.mich8bsp

import java.util.UUID

case class Player(id: UUID, roomId: UUID, color: EPlayerColor)

object Player {
  def create(roomId: UUID, color: EPlayerColor): Player = Player(
    id = UUID.randomUUID(),
    roomId = roomId,
    color = color)
}
