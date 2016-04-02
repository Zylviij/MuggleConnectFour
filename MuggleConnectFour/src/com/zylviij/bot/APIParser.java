package com.zylviij.bot;

import java.util.LinkedList;

public class APIParser extends Thread {
    private int                your_botid;
    private int                timebank;
    private int                time_per_move;
    private String[]           player_names;
    private String             your_bot;
    private int                round;
    private volatile boolean alive = false;
    
    private final Bot          parent;
    
    private LinkedList<String> input = new LinkedList<String>();
    
    public APIParser(Bot parent) {
        this.parent = parent;
    }
    
    public void add(String in) {
        input.add(in);
    }
    
    public void run() {
        alive = true;
        while (alive) {
            if (!input.isEmpty()) {
                String line = input.poll();
                
                if (line.length() == 0) continue;
                
                String[] parts = line.split(" ");
                
                try {
                    if (parts[0].equals("settings")) {
                        if (parts[1].equals("timebank")) {
                            timebank = Integer.parseInt(parts[2]);
                            if (timebank != 10000) throw new Exception("Unexpected timebank. Found: " + timebank);
                        } else if (parts[1].equals("time_per_move")) {
                            time_per_move = Integer.parseInt(parts[2]);
                            if (time_per_move != 500) throw new Exception("Unexpected time_per_move. Found: " + time_per_move);
                        } else if (parts[1].equals("player_names")) {
                            player_names = parts[2].split(",");
                            if (player_names.length != 2) throw new Exception("Unexpected player_names length. Found: " + player_names.length);
                            // TODO perform more checks
                        } else if (parts[1].equals("your_bot")) {
                            your_bot = parts[2];
                            if (!your_bot.equals("player1") && !your_bot.equals("player2")) throw new Exception("Unexpected your_bot. Found: " + your_bot);
                        } else if (parts[1].equals("field_columns")) {
                            if (Integer.parseInt(parts[2]) != 7) throw new Exception("Column count is wrong.");
                        } else if (parts[1].equals("field_rows")) {
                            if (Integer.parseInt(parts[2]) != 6) throw new Exception("Row count is wrong.");
                        } else if (parts[1].equals("your_botid")) {
                            your_botid = Integer.parseInt(parts[2]);
                            if (your_botid != 1 && your_botid != 2) throw new Exception("Unexpected your_botid. Found: " + your_botid);
                        } else throw new Exception("Unknown API. Use timebank, time_per_move, player_names, your_bot, your_botid, field_columns, or field_rows. Found:" + parts[1]);
                    } else if (parts[0].equals("update")) {
                        if (parts[1].equals("game")) {
                            if (parts[2].equals("round")) {
                                round = Integer.parseInt(parts[3]);
                            } else if (parts[2].equals("field")) {
                                parent.fieldParse(parts[3]);
                            } else throw new Exception("Unknown API. Found: " + parts[2]);
                        } else throw new Exception("Unknown API. Use game. Found: " + parts[1]);
                    } else if (parts[0].equals("action")) {
                        if (parts[1].equals("move")) {
                            parent.getMove(round, Integer.parseInt(parts[2]));
                        } else throw new Exception("Unknown API. Found: " + parts[1]);
                    } else throw new Exception("Unknown API. Use settings, update, or action. Found: " + parts[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void free() {
        alive = false;
    }
}
