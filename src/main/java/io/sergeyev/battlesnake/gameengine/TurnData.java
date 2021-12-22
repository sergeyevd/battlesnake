package io.sergeyev.battlesnake.gameengine;

public record TurnData(
        Game game,
        int turn,
        Board board,
        Battlesnake you
) {
}
