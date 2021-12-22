package io.sergeyev.battlesnake.gameengine;

public record TurnAction(
        MoveDirection move,
        String shout
) {
}
