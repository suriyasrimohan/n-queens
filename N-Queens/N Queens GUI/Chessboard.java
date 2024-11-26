import java.util.ArrayList;

public class Chessboard {

    private int[][] board;
    private int numOfQueens = 0;
    private int rows, columns;
    private ArrayList<Queen> queens = new ArrayList<>();
    private ArrayList<Queen> fixedQueens = new ArrayList<>();

    public Chessboard(int r, int c) {
        rows = r;
        columns = c;
        if (r > c) {
            rows = c;
            columns = r;
        }
        board = new int[rows][columns];
    }

    public int[][] getBoard() {
        return board;
    }

    public boolean checkIfPlace(int r, int c) {
        return r < rows && c < columns && board[r][c] >= 0;
    }

    public boolean isQueenHere(int r, int c) {
        for (Queen queen : queens) {
            if (queen.getRow() == r && queen.getColumn() == c) {
                return true;
            }
        }
        return false;
    }

    public boolean isBoardEmpty() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (isQueenHere(r, c)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void placeOrRemoveQueen(int r, int c, int incOrDec) {
        if (incOrDec == -1) {
            queens.add(new Queen(r, c));
            numOfQueens++;
        } else if (incOrDec == 1) {
            numOfQueens--;
            queens.removeIf(queen -> queen.getRow() == r && queen.getColumn() == c);
        }
        board[r][c] += incOrDec;

        for (int i = 0; i < columns; i++) {
            if (i != c) board[r][i] += incOrDec;
        }

        for (int i = 0; i < rows; i++) {
            if (i != r) board[i][c] += incOrDec;
        }

        updateDiagonal(r, c, incOrDec, 1, 1);
        updateDiagonal(r, c, incOrDec, -1, -1);
        updateDiagonal(r, c, incOrDec, 1, -1);
        updateDiagonal(r, c, incOrDec, -1, 1);
    }

    private void updateDiagonal(int r, int c, int incOrDec, int rowInc, int colInc) {
        int tempR = r + rowInc;
        int tempC = c + colInc;
        while (tempR >= 0 && tempR < rows && tempC >= 0 && tempC < columns) {
            board[tempR][tempC] += incOrDec;
            tempR += rowInc;
            tempC += colInc;
        }
    }

    public void removeAllQueens() {
        fixedQueens.clear();
        while (!queens.isEmpty()) {
            Queen queen = queens.remove(queens.size() - 1);
            placeOrRemoveQueen(queen.getRow(), queen.getColumn(), 1);
        }
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                board[r][c] = 0;
            }
        }
    }

    public boolean solve(int r) {
        if (isSolution()) return true;
        if (!areQueensAttacked()) {
            for (int c = 0; c < columns; c++) {
                if (isFixedQueenOnRow(r)) {
                    return solve(r + 1);
                } else if (board[r][c] == 0) {
                    placeOrRemoveQueen(r, c, -1);
                    if (solve(r + 1)) return true;
                    placeOrRemoveQueen(r, c, 1);
                }
            }
        }
        return false;
    }

    public String boardToString() {
        StringBuilder boardString = new StringBuilder("\n");
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (board[r][c] == 0) {
                    boardString.append("00 ");
                } else {
                    if (isQueenHere(r, c)) {
                        boardString.append("Qn ");
                    } else {
                        boardString.append(board[r][c]).append(" ");
                    }
                }
            }
            boardString.append("\n");
        }
        return boardString.toString();
    }

    public boolean isFixedQueenOnRow(int r) {
        for (Queen queen : fixedQueens) {
            if (queen.getRow() == r) return true;
        }
        return false;
    }

    public Queen getQueenAt(int r, int c) {
        for (Queen queen : queens) {
            if (queen.getRow() == r && queen.getColumn() == c) return queen;
        }
        return null;
    }

    public Queen getFixedQueenAt(int r, int c) {
        for (Queen queen : fixedQueens) {
            if (queen.getRow() == r && queen.getColumn() == c) return queen;
        }
        return null;
    }

    public boolean areQueensAttacked() {
        for (Queen queen : queens) {
            if (board[queen.getRow()][queen.getColumn()] < -1) return true;
        }
        return false;
    }

    public void addFixedQueen(Queen queen) {
        queen.setFixed(true);
        fixedQueens.add(queen);
    }

    public void removeFixedQueen(int r, int c) {
        fixedQueens.removeIf(queen -> queen.getRow() == r && queen.getColumn() == c);
    }

    public int tooFewManyOrMax() {
        if (numOfQueens > rows) return 1;
        if (numOfQueens < rows) return -1;
        return 0;
    }

    public boolean isSolution() {
        return !areQueensAttacked() && numOfQueens == rows;
    }

    public ArrayList<Queen> getQueens() {
        return queens;
    }

    public int getNumOfQueens() {
        return numOfQueens;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setAllQueensToFixed() {
        for (Queen queen : queens) {
            queen.setFixed(true);
            addFixedQueen(queen);
        }
    }
}
