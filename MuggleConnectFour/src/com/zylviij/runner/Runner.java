package com.zylviij.runner;

import com.zylviij.bot.Bot;
import com.zylviij.bot.Field;

public class Runner {
    public static void main(String[] args) {
        Field field = new Field();
        Bot one = new Bot();
        Bot two = new Bot();
        int count = 1;
        try {
            one.play("settings timebank 10000");
            one.play("settings time_per_move 500");
            one.play("settings player_names player1,player2");
            one.play("settings your_bot player1");
            one.play("settings your_botid 1");
            one.play("settings field_columns 7");
            one.play("settings field_rows 6");
           
            two.play("settings timebank 10000");
            two.play("settings time_per_move 500");
            two.play("settings player_names player1,player2");
            two.play("settings your_bot player1");
            two.play("settings your_botid 1");
            two.play("settings field_columns 7");
            two.play("settings field_rows 6");
                        
            while (!field.isFull()) { // TODO replace with won/tied
                one.play("update game round " + count++);
                one.play("update game field " + field.toString());
                System.out.print("Player " + Field.toColor(1) + "'s Turn: ");
                field.addDisc(one.play("action move 10000"), 1); // FIXME deal with null
                field.print();
                if (field.won(1)) {
                    break;
                }
                
                System.out.print("Player " + Field.toColor(2) + "'s Turn:");
                two.play("update game round " + count++);
                two.play("update game field " + field.toString());
                field.addDisc(two.play("action move 10000"), 2); // FIXME deal with null
                field.print();
                if (field.won(2)) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
