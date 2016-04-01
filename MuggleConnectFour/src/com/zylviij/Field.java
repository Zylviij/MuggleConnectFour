package com.zylviij;

/**
 * Minimal representation of field.
 * 
 * @author Zylviij
 */
public class Field {
    
    private int[][]          board   = new int[7][6]; // 0: blank, 1: player one, 2: player two
    
    private static final int COLUMNS = 7;
    private static final int ROWS    = 6;
    
    public void setColumns(int cols) throws Exception {
        if (cols != COLUMNS) throw new Exception("Column count is wrong.");
    }
    
    public void setRows(int rows) throws Exception {
        if (rows != ROWS) throw new Exception("Row count is wrong.");
    }
    
    public void clearBoard() {
        for (int x = 0; x < COLUMNS; x++) {
            for (int y = 0; y < ROWS; y++) {
                board[x][y] = 0;
            }
        }
    }
    
    public void addDisc(int col, int disc) throws Exception {
        if (col < 0 || col >= COLUMNS) throw new Exception("Column out of range.");
        else {
            for (int y = 0; y < ROWS; y++) {
                if (board[col][y] == 0) { // space was empty
                    board[col][y] = disc; // filled with player number
                    return;
                }
            }
        }
        throw new Exception("Disc was not placed. Column was full?");
    }
    
    public void parseFromString(String s) {
        int counter = 0;
        for (int y = 0; y < ROWS; y++)
            for (int x = 0; x < COLUMNS; x++)
                board[x][y] = Character.getNumericValue(s.charAt(counter++ * 2));
    }
    
    public int getDisc(int column, int row) {
        return board[column][row];
    }
    
    public Boolean isValidMove(int column) {
        return (board[column][0] == 0);
    }
    
    @Override
    public String toString() {
        String r = "";
        int counter = 0;
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                if (counter > 0) {
                    r += ",";
                }
                r += board[x][y];
                counter++;
            }
        }
        return r;
    }
    
    public boolean isFull() {
        for (int x = 0; x < COLUMNS; x++)
            for (int y = 0; y < ROWS; y++)
                if (board[x][y] == 0) return false; // At least one cell is not filled
        // All cells are filled
        return true;
    }
    
    public boolean isColumnFull(int column) {
        return (board[column][0] != 0);
    }
}
