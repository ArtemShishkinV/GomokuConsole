package ru.shishkin.model;

public class Bot {
    //Центр поля, для первого хода
    private static final int MID_ROW = Game.getNumRow() / 2;
    private static final int MID_COL = Game.getNumColumn() / 2;

    private Board board;
    private int stoneColor = 0;

    Bot(int stoneColor, Board board) {
        this.stoneColor = stoneColor;
        this.board = board;
    }

    public int getStoneColor() {
        return stoneColor;
    }

    /**
     * Основной метод, в котором бот делает ход, возвращает координаты хода
     Алгоритм следующий:
     1) определить, первый ли камень на доске
     2) определить, сколько очков может получить бот
     3) определить сколько очков получит игрок, если игрок выиграет следующим ходом, то бот должен защищаться
     4) если игрок не выиграет следующим ходом, пусть бот атакует локацию, рассчитанную в 2)
     */
    public Tuple play() {
        Tuple result = new Tuple(-1, -1);
        if (board.getNumStones() == 0) {
            result.row_co = MID_ROW;
            result.col_co = MID_COL;
            board.placeStone(result.row_co, result.col_co, stoneColor);
            return result;
        }
        // Проверка очков бота
        int maxScore = -1;
        for (int i = 0; i < board.getNumRow(); ++i) {
            for (int j = 0; j < board.getNumCol(); ++j) {
                if (board.getStone(i, j) != 0) continue;
                int currentScore = board.scoreAfter(i, j, stoneColor);
                if (stoneColor == -1) currentScore = -currentScore;
                if (currentScore > maxScore) {
                    result.row_co = i;
                    result.col_co = j;
                    maxScore = currentScore;
                } else if (currentScore == maxScore) {
                    int opponentScore_max = board.scoreAfter(result.row_co, result.col_co, -stoneColor);
                    int opponentScore_current = board.scoreAfter(i, j, -stoneColor);
                    if (Math.abs(opponentScore_current) > Math.abs(opponentScore_max)) {
                        result.row_co = i;
                        result.col_co = j;
                        maxScore = currentScore;
                    }
                }
            }
        }
        if (board.oneStepToWin(result.row_co, result.col_co, stoneColor)) {
            board.placeStone(result.row_co, result.col_co, stoneColor);
            return result;
        }
        // Проверка очков игрока
        for (int i = 0; i < board.getNumRow(); ++i) {
            for (int j = 0; j < board.getNumCol(); ++j) {
                if (board.getStone(i, j) != 0) continue;
                int opponentScore = Math.abs(board.scoreAfter(i, j, -stoneColor));
                if (opponentScore >= Math.abs(4 * stoneColor)) {
                    board.placeStone(i, j, stoneColor);
                    result.row_co = i;
                    result.col_co = j;
                    return result;
                }
            }
        }
        board.placeStone(result.row_co, result.col_co, stoneColor);
        return result;
    }
}
