package ru.shishkin;

import ru.shishkin.controller.GameController;
import ru.shishkin.model.Game;

public class Main {
    public static void main(String[] args) {
        GameController gameController = new GameController(new Game());
        gameController.game();
    }
}
