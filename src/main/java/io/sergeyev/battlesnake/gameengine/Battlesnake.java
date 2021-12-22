package io.sergeyev.battlesnake.gameengine;

public record Battlesnake(
        String id,
        String name,
        int health,
        Coordinate[] body,
        String latency,
        Coordinate head,
        int length,
        String shout,
        String squad
) {
}
