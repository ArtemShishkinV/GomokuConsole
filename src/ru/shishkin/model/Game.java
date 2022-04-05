package ru.shishkin.model;

import java.util.Scanner;
public class Game {
    private static final int NUM_ROW = 7;
    private static final int NUM_COLUMN = 7;

    private Board boardObj;

    private Player player;

    private Bot bot;

    private boolean playerFirst;

    public Game() {
        boardObj = new Board(NUM_ROW, NUM_COLUMN);
    }

    public static int getNumRow() {
        return NUM_ROW;
    }

    public static int getNumColumn() {
        return NUM_COLUMN;
    }

    public Player getPlayer() {
        return player;
    }

    public Bot getBot() {
        return bot;
    }

    public Board getBoardObj() {
        return boardObj;
    }

    public boolean isPlayerFirst() {
        return playerFirst;
    }

    public String start() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(determineGoesFirst());
        this.bot = new Bot((playerFirst ? -1 : 1), boardObj);
        this.player = new Player((playerFirst ? 1 : -1), boardObj);

        if(!playerFirst) bot.play();

        stringBuilder.append(boardObj);

        return stringBuilder.toString();
    }

    public String play(Tuple tuple) {
        return turnPlayer(tuple) +
                turnBot() + getBoardObj();
    }

    private String turnPlayer(Tuple tuple) {
        StringBuilder stringBuilder = new StringBuilder();
        player.putStone(tuple.row_co, tuple.col_co, player.getStoneColour());
        stringBuilder.append(getResultGameChecker("You"));
        return stringBuilder.toString();
    }

    private String turnBot() {
        StringBuilder stringBuilder = new StringBuilder();
        bot.play();
        stringBuilder.append(getResultGameChecker("AI"));
        return stringBuilder.toString();
    }

    private String getResultGameChecker(String player) {
        if (GameChecker.ifWin(this.boardObj)) return GameChecker.getWinMessage(this, player);
        if (GameChecker.isFull(this.boardObj)) return GameChecker.getFullMessage(this);
        return "";
    }

    public Tuple getTurn(Scanner scanner) {
        return new Tuple(scanner.nextInt(), scanner.nextInt());
    }

    private String determineGoesFirst() {
        StringBuilder stringBuilder = new StringBuilder();
        this.playerFirst = false;
        stringBuilder.append("Flipping a coin...\n");
        if (Math.random() < 0.5) {
            stringBuilder.append("You go first...\n");
            this.playerFirst = true;
        } else {
            stringBuilder.append("Computer goes first...\n");
        }
        return stringBuilder.toString();
    }

}
