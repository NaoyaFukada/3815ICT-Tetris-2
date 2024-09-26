// BoardEvaluator.java
package model;

public class BoardEvaluator {

    public int evaluateBoard(Board board) {
        int heightScore = getHeight(board);
        int holesScore = getHoles(board);
        int linesCleared = getClearedLines(board);
        int bumpinessScore = getBumpiness(board);

        return (-4 * heightScore) + (3 * linesCleared) - (5 * holesScore)
                - (2 * bumpinessScore);
    }

    private int getHeight(Board board) {
        int height = 0;
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                if (board.getCellColor(y, x) != null) {
                    height = Math.max(height, board.getHeight() - y);
                    break;
                }
            }
        }
        return height;
    }

    private int getHoles(Board board) {
        int holes = 0;
        for (int x = 0; x < board.getWidth(); x++) {
            boolean foundBlock = false;
            for (int y = 0; y < board.getHeight(); y++) {
                if (board.getCellColor(y, x) != null) {
                    foundBlock = true;
                } else if (foundBlock && board.getCellColor(y, x) == null) {
                    holes++;
                }
            }
        }
        return holes;
    }

    private int getClearedLines(Board board) {
        int clearedLines = 0;
        for (int y = 0; y < board.getHeight(); y++) {
            boolean isLineFull = true;
            for (int x = 0; x < board.getWidth(); x++) {
                if (board.getCellColor(y, x) == null) {
                    isLineFull = false;
                    break;
                }
            }
            if (isLineFull) {
                clearedLines++;
            }
        }
        return clearedLines;
    }

    private int getBumpiness(Board board) {
        int bumpiness = 0;
        for (int x = 0; x < board.getWidth() - 1; x++) {
            int colHeight1 = getColumnHeight(board, x);
            int colHeight2 = getColumnHeight(board, x + 1);
            bumpiness += Math.abs(colHeight1 - colHeight2);
        }
        return bumpiness;
    }

    private int getColumnHeight(Board board, int col) {
        for (int y = 0; y < board.getHeight(); y++) {
            if (board.getCellColor(y, col) != null) {
                return board.getHeight() - y;
            }
        }
        return 0;
    }
}
