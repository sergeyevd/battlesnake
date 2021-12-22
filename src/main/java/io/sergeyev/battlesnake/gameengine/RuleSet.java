package io.sergeyev.battlesnake.gameengine;

public record RuleSet(
        String name,
        String version,
        RuleSetSettings settings
) {
}
