package ru.shishkin.model;

public class GameChecker {
    private GameChecker() {};

    public static boolean isFull(Board board) {
        for (int[] arr : board.getBoard()) {
            for (int value : arr) {
                if (value == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean ifWin(Board board) {
        for (int[] row : board.getBoard()) {
            if (horizontalCheck(row)) {
                return true;
            }
        }
        for (int col = 0; col < board.getNumCol(); ++col) {
            if (verticalCheck(board.getBoard(), col)) {
                return true;
            }
        }
        return diagonalCheck(board.getBoard()) || reverseDiagonalCheck(board.getBoard());
    }

    private static boolean horizontalCheck(int[] row) {
        int colour = 0;
        int counter = 0;
        for (int i : row) {
            if (i != 0 && i == colour) {
                ++counter;
            } else {
                counter = 0;
                if (i != 0) {
                    colour = i;
                    ++counter;
                }
            }
            if (counter == 5) {
                return true;
            }
        }
        return false;
    }

    private static boolean verticalCheck(int[][] arr, int col) {
        int[] vertArr = new int[arr.length];
        for (int row = 0; row < arr.length; ++row) {
            vertArr[row] = arr[row][col];
        }
        return horizontalCheck(vertArr);
    }

    private static boolean diagonalCheck(int[][] arr) {
        int min = Math.min(arr.length, arr[0].length);
        int[] diagArr = new int[min];
        for (int row = 0; row < min; ++row) {
            diagArr[row] = arr[row][row];
        }
        if (horizontalCheck(diagArr)) {
            return true;
        }

        for (int row = 0, col = 1; col < arr[0].length; ++col) {
            for (int dia_row = 0, dia_col = col; dia_col < arr[0].length; ++dia_row, ++dia_col) {
                diagArr[dia_row] = arr[dia_row][dia_col];
            }
            if (horizontalCheck(diagArr)) {
                return true;
            } else {
                diagArr = new int[min];
            }
        }

        for (int row = 1, col = 0; row < arr.length; ++row) {
            for (int dia_row = row, dia_col = 0; dia_row < arr.length; ++dia_row, ++dia_col) {
                diagArr[dia_col] = arr[dia_row][dia_col];
            }
            if (horizontalCheck(diagArr)) {
                return true;
            } else {
                diagArr = new int[min];
            }
        }
        return false;
    }

    private static boolean reverseDiagonalCheck(int[][] arr) {
        int[][] reverse = new int[arr.length][arr[0].length];
        for (int row = 0; row < arr.length; ++row) {
            for (int col = 0; col < arr[0].length; ++col) {
                reverse[row][col] = arr[row][arr[0].length - 1 - col];
            }
        }
        return diagonalCheck(reverse);
    }


}
