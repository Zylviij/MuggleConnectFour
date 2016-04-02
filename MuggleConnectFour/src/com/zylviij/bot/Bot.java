package com.zylviij.bot;

public class Bot {
    
    private final APIParser parser = new APIParser(this);
    private final Searcher search = new Searcher(this);
    
    private int play;
    
    public Bot() {
        new Thread(parser).start();
        new Thread(search).start();
    }
    
    public void parse(String in) {
        parser.add(in);
    }
    
    public void fieldParse(String s) {
        search.parseField(s);
    }

//    public int play(String s) throws Exception {
//        parser.add(s);
//        return (new Parser(new Bot())).parse(s);
//    }
    
    public void getMove(int round, int timebank) {
        search.getBestMove(round, timebank);
    }
    
    public void reset() {
        play = -1;
    }
    
    public int getMove() {
        return play;
    }
    
    public static void main(String[] args) {
//        try {
//            new Parser(new Bot()).run();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void setPlay(int play) {
        this.play = play;
    }
    
    public void free() {
        search.free();
        parser.free();
    }
}
