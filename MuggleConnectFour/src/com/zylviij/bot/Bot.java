package com.zylviij.bot;

public class Bot {
    
    public int play(String s) throws Exception {
        return (new Parser(new Bot())).parse(s);
    }
    
    public int makeTurn(int round, int timebank, Field field) {
        return (int) (Math.random() * 7);
    }
        
    public static void main(String[] args) {
        try {
            new Parser(new Bot()).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
