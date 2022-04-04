package ru.shishkin.model;

import java.util.Scanner;


/**
 * Record the black stone as 1 on board, and -1 for the white stone
 */
public class Game {
    private static final int NUM_ROW = 7;
    private static final int NUM_COLUMN = 7;

    private Board boardObj;

    private int[][] board;

    private Player player;

    private Bot bot;

    private boolean playerFirst;

    public Game() {
        boardObj = new Board(NUM_ROW, NUM_COLUMN);
        this.board = boardObj.getBoard();
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

    /**
     * Initializes the game and flips a coin
     * If heads, player plays first
     * otherwise bot plays first
     */
    public String start() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(determineGoesFirst());

        this.bot = new Bot((playerFirst ? -1 : 1), boardObj);
        this.player = new Player((playerFirst ? 1 : -1), boardObj);

        stringBuilder.append(boardObj);

        return stringBuilder.toString();
    }

    public String play(Tuple tuple) {
        StringBuilder stringBuilder = new StringBuilder();
        // player place the stone..
        player.putStone(tuple.row_co, tuple.col_co, player.getStoneColour());
        if (GameChecker.ifWin(this.boardObj)) {
            stringBuilder.append("You Win!").append("\n");
            stringBuilder.append(boardObj);
            return stringBuilder.toString();
        } else if (GameChecker.isFull(this.boardObj)) {
            stringBuilder.append("Board Full..").append("\n");
            stringBuilder.append(boardObj);
            return stringBuilder.toString();
        }
        // bot place the stone..
        bot.play();
        if (GameChecker.ifWin(this.boardObj)) {
            stringBuilder.append("AI win!").append("\n");
            stringBuilder.append(boardObj);
            return stringBuilder.toString();
        } else if (GameChecker.isFull(this.boardObj)) {
            stringBuilder.append("Board Full..").append("\n");
            stringBuilder.append(boardObj);
            return stringBuilder.toString();
        }
        stringBuilder.append(boardObj.toString());
        return stringBuilder.toString();
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
