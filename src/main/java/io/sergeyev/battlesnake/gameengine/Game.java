package io.sergeyev.battlesnake.gameengine;

public record Game(
        String id,
        RuleSet ruleset,
        int timeout, //millis
        String source
) {
}
