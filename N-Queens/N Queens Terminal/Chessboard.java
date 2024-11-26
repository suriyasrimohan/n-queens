import java.util.*;

public class Chessboard {
    private int board[][];
    private int numOfQueens, numOfSolutions, queenArrayIndex, rows, columns;
    private ArrayList<Queen[]> queenSolutions = new ArrayList<>();
    private Queen[] queens;

    public Chessboard(int r, int c) {
        numOfQueens = 0;
        numOfSolutions = 0;
        queenArrayIndex = 0;
        rows = r;
        columns = c;
        if (r > c) {
            rows = c;
            columns = r;
        }
        board = new int[rows][columns];
        queens = new Queen[rows];
    }

    public boolean checkIfPlace(int r, int c) {
        boolean canPlace = true;
        if ((r >= rows) || (c >= columns)) {
            canPlace = false;
        } else if ((board[r][c] < 0)) {
            canPlace = false;
        }
        return canPlace;
    }

    public boolean isQueenHere(int r, int c) {
        boolean isHere = false;
        if (numOfQueens != 0) {
            for (int i = 0; i < numOfQueens; i++) {
                if ((queens[i].getRow() == r) && (queens[i].getColumn() == c)) {
                    isHere = true;
                }
            }
        }
        return isHere;
    }

    public void placeOrRemoveQueen(int r, int c, int incOrDec) {
        if (incOrDec == -1) {
            Queen queen = new Queen(r, c);
            queens[queenArrayIndex] = queen;
            numOfQueens++;
            queenArrayIndex++;
        } else if (incOrDec == 1) {
            numOfQueens--;
            queenArrayIndex--;
            queens[queenArrayIndex] = null;
        }

        board[r][c] += incOrDec;

        for (int i = 0; i < columns; i++) {
            if (i != c) {
                board[r][i] += incOrDec;
            }
        }

        for (int i = 0; i < rows; i++) {
            if (i != r) {
                board[i][c] += incOrDec;
            }
        }

        int tempR = r + 1;
        int tempC = c + 1;

        while ((tempR < rows) && (tempC < columns)) {
            board[tempR][tempC] += incOrDec;
            tempR++;
            tempC++;
        }

        tempR = r - 1;
        tempC = c - 1;

        while ((tempR < rows) && (tempC < columns) && (0 <= tempR) && (0 <= tempC)) {
            board[tempR][tempC] += incOrDec;
            tempR--;
            tempC--;
        }

        tempR = r + 1;
        tempC = c - 1;

        while ((tempR < rows) && (tempC < columns) && (0 <= tempR) && (0 <= tempC)) {
            board[tempR][tempC] += incOrDec;
            tempR++;
            tempC--;
        }

        tempR = r - 1;
        tempC = c + 1;

        while ((tempR < rows) && (tempC < columns) && (0 <= tempR) && (0 <= tempC)) {
            board[tempR][tempC] += incOrDec;
            tempR--;
            tempC++;
        }
    }

    public void removeAllQueens() {
        if (numOfQueens != 0) {
            for (int i = (numOfQueens - 1); i >= 0; i--) {
                placeOrRemoveQueen(queens[i].getRow(), queens[i].getColumn(), 1);
            }
        }
    }

    public boolean solve(int r) {
        boolean ans = false;

        if (r == rows) {
            ans = true;
        } else {
            for (int c = 0; (c < columns) && !ans; c++) {
                if (board[r][c] == 0) {
                    placeOrRemoveQueen(r, c, -1);
                    ans = solve((r + 1));
                    if (ans) {
                        ans = false;
                        Queen[] solution = new Queen[rows];
                        for (int i = 0; i < rows; i++) {
                            if (queens[i] != null) {
                                solution[i] = new Queen(queens[i].getRow(), queens[i].getColumn());
                            }
                        }
                        queenSolutions.add(solution);
                        numOfSolutions++;
                    }
                    placeOrRemoveQueen(r, c, 1);
                }
            }
        }
        return ans;
    }

    public int getNumOfSolutions() {
        return numOfSolutions;
    }

    public List<Queen[]> getQueenArraySolutions() {
        return queenSolutions;
    }

    public String boardToString() {
        StringBuilder boardString = new StringBuilder("\n");
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (isQueenHere(r, c)) {
                    boardString.append("[Q]");
                } else {
                    boardString.append("[_]");
                }
            }
            boardString.append("\n");
        }
        return boardString.toString();
    }
}
