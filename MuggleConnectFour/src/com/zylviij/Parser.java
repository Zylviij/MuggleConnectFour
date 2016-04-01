package com.zylviij;

import java.util.Scanner;

public class Parser {
    private final Bot     bot;
    private final Scanner in;
    private final Field   field;
    
    private int           id;
    
    Parser(Bot bot) {
        this.in = new Scanner(System.in);
        this.field = new Field();
        this.bot = bot;
    }
    
    // TODO fucking handle all possible cases. God this is ugly
    public void run() throws Exception {
        while (in.hasNextLine()) {
            String line = in.nextLine();
            
            if (line.length() == 0) {
                continue;
            }
            
            String[] parts = line.split(" ");
            
            if (parts[0].equals("settings")) {
                if (parts[1].equals("field_columns")) {
                    field.setColumns(Integer.parseInt(parts[2]));
                } else if (parts[1].equals("field_rows")) {
                    field.setRows(Integer.parseInt(parts[2]));
                } else if (parts[1].equals("your_botid")) {
                    id = Integer.parseInt(parts[2]);
                }
            } else if (parts[0].equals("update")) { /* new field data */
                if (parts[2].equals("field")) {
                    String data = parts[3];
                    field.parseFromString(data); /* Parse Field with data */
                }
            } else if (parts[0].equals("action")) {
                if (parts[1].equals("move")) { /* move requested */
                    int column = bot.makeTurn();
                    System.out.println("place_disc " + column);
                }
            } else {
                System.out.println("unknown command");
            }
        }
    }
}
