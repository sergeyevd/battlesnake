package io.sergeyev.battlesnake.gameengine;

public record SquadRuleSetSettings(
        boolean allowBodyCollisions,
        boolean sharedElimination,
        boolean sharedHealth,
        boolean sharedLength
) {
}
