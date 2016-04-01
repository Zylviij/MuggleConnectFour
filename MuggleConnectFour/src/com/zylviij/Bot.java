package com.zylviij;

public class Bot {
    
    public int makeTurn() {
        return 0;
    }
    
    public static void main(String[] args) {
        try {
            new Parser(new Bot()).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
