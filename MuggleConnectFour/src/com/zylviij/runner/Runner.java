package com.zylviij.runner;

import com.zylviij.bot.Bot;
import com.zylviij.bot.Field;

public class Runner {
    public static void main(String[] args) {
        
        final Field field = new Field();
        final Bot one = new Bot();
        final Bot two = new Bot();
        
        int round = 1;
        
        int bot_one_bank = 10000;
        int bot_two_bank = 10000;
        
        try {
            one.parse("settings timebank " + bot_one_bank);
            one.parse("settings time_per_move 500");
            one.parse("settings player_names player1,player2");
            one.parse("settings your_bot player1");
            one.parse("settings your_botid 1");
            one.parse("settings field_columns 7");
            one.parse("settings field_rows 6");
            
            two.parse("settings timebank " + bot_two_bank);
            two.parse("settings time_per_move 500");
            two.parse("settings player_names player1,player2");
            two.parse("settings your_bot player1");
            two.parse("settings your_botid 1");
            two.parse("settings field_columns 7");
            two.parse("settings field_rows 6");
            
            while (!field.isFull()) {
                one.parse("update game round " + round++);
                one.parse("update game field " + field.toString());
                
                System.out.println("Player " + Field.toColor(1) + "'s Turn: ");
                one.parse("action move " + bot_one_bank);
                
                long one_start = System.currentTimeMillis(); // start time
                while (bot_one_bank - (System.currentTimeMillis() - one_start) > 0) {
                    if (one.getMove() != -1) break;
                }
                if (one.getMove() == -1)  throw new Exception("Ran out of time, player one.");
                field.addDisc(one.getMove(), 1);
                bot_one_bank = bot_one_bank + 500 - (int) (System.currentTimeMillis() - one_start); // end time
                one.reset();
                
                field.print();
                if (field.won(1)) {
                    break;
                }
                
                two.parse("update game round " + round++);
                two.parse("update game field " + field.toString());
                
                System.out.println("Player " + Field.toColor(2) + "'s Turn:");
                two.parse("action move " + bot_two_bank);
                
                long two_start = System.currentTimeMillis(); // start time
                while (bot_two_bank - (System.currentTimeMillis() - two_start) > 0) {
                    if (two.getMove() != -1) break;
                }
                if (two.getMove() == -1)  throw new Exception("Ran out of time, player two.");
                field.addDisc(two.getMove(), 2); // FIXME deal with null
                bot_two_bank = bot_two_bank + 500 - (int) (System.currentTimeMillis() - two_start); // end time
                two.reset();
                
                field.print();
                if (field.won(2)) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        one.free();
        two.free();
    }
}
