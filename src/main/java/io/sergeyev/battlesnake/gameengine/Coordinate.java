package io.sergeyev.battlesnake.gameengine;

public record Coordinate(
        int x,
        int y
) {
    public static Coordinate createNextPosition(Coordinate currentPosition, MoveDirection direction) {
        return switch (direction) {
            case up -> new Coordinate(currentPosition.x(), currentPosition.y() + 1);
            case right -> new Coordinate(currentPosition.x() + 1, currentPosition.y());
            case down -> new Coordinate(currentPosition.x(), currentPosition.y() - 1);
            case left -> new Coordinate(currentPosition.x() - 1, currentPosition.y());
        };
    }
}
