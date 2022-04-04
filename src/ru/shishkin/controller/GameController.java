package ru.shishkin.controller;

import ru.shishkin.model.Game;

import java.util.Scanner;

public class GameController {
    private Game game;
    private Scanner scanner = new Scanner(System.in);

    public GameController(Game game) {
        this.game = game;
    }

    public void game(){
        System.out.println(game.start());
        String play = game.getBoardObj().toString();
        while(play.equals(game.getBoardObj().toString())) {
            play = game.play(game.getTurn(scanner));
            System.out.println(play);
        }
    }
}
