package io.sergeyev.battlesnake.controller;

public record MainStatus(
        String apiversion,
        String author,
        String color,
        String head,
        String tail,
        String version
) {
}
