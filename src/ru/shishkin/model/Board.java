package ru.shishkin.model;

public class Board {
    private int numRow;
    private int numCol;

    private int[][] board;
    private int numStones;

    Board(int numRow, int numCol) {
        this.board = new int[numRow][numCol];
        this.numRow = numRow;
        this.numCol = numCol;
        numStones = 0;
    }

    @Override
    public String toString() {
        StringBuilder board = new StringBuilder();
        for (int i = 0; i < numRow; i++) {
            for (int j = 0; j < numCol; j++) board.append(StoneType.getSignById(this.board[i][j])).append("\t");
            board.append("\n");
        }
        return board.toString();
    }

    public void placeStone(int row, int col, int colour) {
        board[row][col] = colour;
        ++numStones;
    }

    public void removeStone(int row, int col) {
        board[row][col] = 0;
        --numStones;
    }

    public int getStone(int row, int col) {
        return board[row][col];
    }

    public int[][] getBoard() {
        return board;
    }

    public int getNumStones() {
        return numStones;
    }

    public int getNumRow() {
        return numRow;
    }

    public int getNumCol() {
        return numCol;
    }

    public int scoreAfter(int row, int col, int color) {
        int score[] = {0, 0, 0, 0};

        // horizontal check
        for (int i = col - 1; i >= 0; --i) {
            if (board[row][i] == color) {
                score[0] += color;
            } else {
                score[0] += board[row][i];
                break;
            }
        }
        for (int i = col + 1; i < numCol; ++i) {
            if (board[row][i] == color) {
                score[0] += color;
            } else {
                score[0] += board[row][i];
                break;
            }
        }
        // vertical check
        for (int i = row - 1; i >= 0; --i) {
            if (board[i][col] == color) {
                score[1] += color;
            } else {
                score[1] += board[i][col];
                break;
            }
        }
        for (int i = row + 1; i < numRow; ++i) {
            if (board[i][col] == color) {
                score[1] += color;
            } else {
                score[1] += board[i][col];
                break;
            }
        }

        // diagonal check
        for (int i = 1; row - i >= 0 && col - i >= 0; ++i) {
            if (board[row - i][col - i] == color) {
                score[2] += color;
            } else {
                score[2] += board[row - i][col - i];
                break;
            }
        }
        for (int i = 1; row + i < numRow && col + i < numCol; ++i) {
            if (board[row + i][col + i] == color) {
                score[2] += color;
            } else {
                score[2] += board[row + i][col + i];
                break;
            }
        }
        // reverse diagonal check
        for (int i = 1; row + i < numRow && col - i >= 0; ++i) {
            if (board[row + i][col - i] == color) {
                score[3] += color;
            } else {
                score[3] += board[row + i][col - i];
                break;
            }
        }
        for (int i = 1; row - i >= 0 && col + i < numCol; ++i) {
            if (board[row - i][col + i] == color) {
                score[3] += color;
            } else {
                score[3] += board[row - i][col + i];
                break;
            }
        }
        if ((Math.abs(score[0]) >= 2 && Math.abs(score[1]) >= 2) ||
                (Math.abs(score[0]) >= 2 && Math.abs(score[2]) >= 2) ||
                (Math.abs(score[0]) >= 2 && Math.abs(score[3]) >= 2) ||
                (Math.abs(score[1]) >= 2 && Math.abs(score[2]) >= 2) ||
                (Math.abs(score[1]) >= 2 && Math.abs(score[3]) >= 2) ||
                (Math.abs(score[2]) >= 2 && Math.abs(score[3]) >= 2)) {
            return 5 * color;
        } else if (oneStepToWin(row, col, color)) {
            return 6 * color;
        } else if (color == 1) {
            return Math.max(Math.max(score[0], score[1]), Math.max(score[2], score[3])) + 1;
        } else {
            return Math.min(Math.min(score[0], score[1]), Math.min(score[2], score[3])) - 1;
        }
    }

    public boolean oneStepToWin(int row, int col, int colour) {
        this.placeStone(row, col, colour);
        boolean result = GameChecker.ifWin(this);
        this.removeStone(row, col);
        return result;
    }

}
