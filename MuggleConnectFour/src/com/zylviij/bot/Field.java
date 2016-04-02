package com.zylviij.bot;

/**
 * Minimal representation of field.
 * 
 * @author Zylviij
 */
public class Field {
    
    private static final int COLUMNS = 7;
    private static final int ROWS    = 6;
    
    private int[][]          board   = new int[COLUMNS][ROWS];
    
    public void clearBoard() {
        for (int x = 0; x < COLUMNS; x++)
            for (int y = 0; y < ROWS; y++)
                board[x][y] = 0;
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
    
    public boolean won(int color) throws Exception {
        int tallest = tallest();
        
        // horizontal
        for (int y = 0; y < tallest; y++)
            for (int x = 0; x < COLUMNS - 3; x++)
                if (board[x][y] == color && board[x + 1][y] == color && board[x + 2][y] == color && board[x + 3][y] == color) {
                    System.out.println("Horizontal: (" + x + ", " + y + ")");
                    System.out.println(Field.toColor(color) + " won!");
                    return true;
                }
        
        // vertical
        for (int x = 0; x < COLUMNS; x++)
            for (int y = 0; y < ROWS - (tallest - 1); y++)
                if (board[x][y] == color && board[x][y + 1] == color && board[x][y + 2] == color && board[x][y + 3] == color) {
                    System.out.println("Vertical: (" + x + ", " + y + ")");
                    System.out.println(Field.toColor(color) + " won!");
                    return true;
                }
        
        // / diagonal FIXME
        for (int x = 0; x < COLUMNS - 3; x++)
            for (int y = 0; y < ROWS - (tallest - 1); y++)
                if (board[x][y] == color && board[x + 1][y + 1] == color && board[x + 2][y + 2] == color && board[x + 3][y + 3] == color) {
                    System.out.println("Diagonal /: (" + x + ", " + y + ")");
                    System.out.println(Field.toColor(color) + " won!");
                    return true;
                }

        // \ diagonal FIXME
        for (int x = 3; x < COLUMNS; x++)
            for (int y = 0; y < ROWS - (tallest - 1); y++)
                if (board[x][y] == color && board[x - 1][y + 1] == color && board[x - 2][y + 2] == color && board[x - 3][y + 3] == color) {
                    System.out.println("Diagonal \\: (" + x + ", " + y + ")");
                    System.out.println(Field.toColor(color) + " won!");
                    return true;
                }

        
        // no other victory, tied/unfinished
        return false; // FIXME
    }
    
    public int tallest() { // TODO needs testing, I'm almost sure its perfect
        int current = 0;
        for (int x = 0; x < COLUMNS; x++) {
            for (int y = current; y < ROWS; y++) {
                if (board[x][y] != 0) {
                    current = y;
                    if (current == ROWS - 1) return current;
                } else break;
            }
        }
        return current + 1;
    }
    
    public void print() throws Exception {
        for (int y = ROWS - 1; y >= 0; y--) {
            for (int x = 0; x < COLUMNS; x++) {
                System.out.print(Field.toColor(board[x][y]));
            }
            System.out.println();
        }
    }
    
    public static String toColor(int in) throws Exception {
        if (in == 0) return "-";
        else if (in == 1) return "X";
        else if (in == 2) return "O";
        else throw new Exception("Unexpected Player ID Color. Found: " + in);
    }
    
    public boolean isFull() {
        for (int x = 0; x < COLUMNS; x++)
            for (int y = 0; y < ROWS; y++)
                if (board[x][y] == 0) return false;
        return true;
    }
}
