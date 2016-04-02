package com.zylviij.bot;

public class Searcher extends Thread {
    
    private final Bot   parent;
    private final Field current = new Field();
    
    private volatile boolean alive = false;
    
    public Searcher(Bot parent) {
        this.parent = parent;
    }
    
    public void parseField(String in) {
        current.parseFromString(in);
    }
    
    public void getBestMove(int round, int timebank) {
        parent.setPlay((int) (Math.random() * 7));
    }
    
    @Override
    public void run() {
        alive = true;
        while (alive) {
        }
    }
    
    public void free() {
        alive = false;
    }
    
}
