package io.sergeyev.battlesnake.gameengine;

public record RuleSetSettings(
        int foodSpawnChance,
        int minimumFood,
        int hazardDamagePerTurn,
        RoyaleRuleSetSettings royale,
        SquadRuleSetSettings squad
) {
}
