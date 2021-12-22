package io.sergeyev.battlesnake.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {

    @GetMapping
    public MainStatus getMainStatus() {
        return new MainStatus("1", "sergeyevd", "#466D1D", "caffeine", "coffee", "1");
    }
}
