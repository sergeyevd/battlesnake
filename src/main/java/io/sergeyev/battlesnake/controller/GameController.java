package io.sergeyev.battlesnake.controller;

import io.sergeyev.battlesnake.gameengine.GameService;
import io.sergeyev.battlesnake.gameengine.TurnAction;
import io.sergeyev.battlesnake.gameengine.TurnData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/move")
    public TurnAction move(@RequestBody TurnData turnData) {
        return gameService.move(turnData);
    }

}
