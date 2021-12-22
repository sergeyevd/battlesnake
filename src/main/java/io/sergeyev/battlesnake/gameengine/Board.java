package io.sergeyev.battlesnake.gameengine;

public record Board(
        int height,
        int width,
        Coordinate[] food,
        Coordinate[] hazards,
        Battlesnake[] snakes
) {
}
