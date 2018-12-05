package com.example.cnssevilleno.activity2;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class SecondActivity extends AppCompatActivity {

    // Arrays
    private static Button cards[] = new Button[16];
    private ArrayList<Integer> iconList = new ArrayList<>();
    // Counters
    private int hits = 0;
    private int misses = 0;
    private int counter = 0;
    // Trackers
    private int[] id = new int[2];
    private int[] value = new int[2];
    // TextViews
    private TextView hitTextView;
    private TextView missTextView;
    private TextView cdTextView;
    // Booleans
    private boolean endgame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // MAIN FUNCTION fired on activity startup

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Initialize elements
        initArrayList();
        initCards();
        initTextViews();

        // Begin countdown of 3 seconds
        countdown();
    }


    public void initArrayList(){
        /* INITIALIZE ARRAYLIST with 16 shuffled numbers

            iconList is an arraylist that contains the values for the
            16 cards. The number of cards divided by 2 (16/2 = 8) will
            be the number of pairs (8). Numbers 1 - 8 exist twice in
            this arraylist, totaling 16 elements.

            The list is shuffled to have the placement of cards
            randomized each game .
         */

        for (int y = 0; y < (cards.length/2); y++) {
            iconList.add(y);
            iconList.add(y);
        }
        Collections.shuffle(iconList);

        /*
            CHEATS for debugging purposes

            The code below prints out the answers to the puzzle in the
            in the log console. It shows the placement of the values
            behind he cards.
         */

        int character = 0;
        String line = "";

        for (int a = 0; a < iconList.size(); a++) {
            line += iconList.get(a) + " ";
            character++;
            if (character == 4) {
                line += "\n";
                character = 0;
            }
        }
        Log.d("Cheats", "\n" +line);
    }


    public void initCards(){
        /*  INITIALIZE CARDS with button references

            There are 16 cards laid out in a 4x4 grid.
            Each card references a button element in the layout

            Get the card's referenced button using the button's ID and
            store them in their respective array indexes for later
            manipulation. Display their respective values until
            countdown to 0 from 3 is complete.
         */

        cards[0] = (Button)findViewById(R.id.button1);
        cards[1] = (Button)findViewById(R.id.button2);
        cards[2] = (Button)findViewById(R.id.button3);
        cards[3] = (Button)findViewById(R.id.button4);
        cards[4] = (Button)findViewById(R.id.button5);
        cards[5] = (Button)findViewById(R.id.button6);
        cards[6] = (Button)findViewById(R.id.button7);
        cards[7] = (Button)findViewById(R.id.button8);
        cards[8] = (Button)findViewById(R.id.button9);
        cards[9] = (Button)findViewById(R.id.button10);
        cards[10] = (Button)findViewById(R.id.button11);
        cards[11] = (Button)findViewById(R.id.button12);
        cards[12] = (Button)findViewById(R.id.button13);
        cards[13] = (Button)findViewById(R.id.button14);
        cards[14] = (Button)findViewById(R.id.button15);
        cards[15] = (Button)findViewById(R.id.button16);

        for(int x = 0; x < cards.length; x++){
            cards[x].setText(iconList.get(x) + "");
        }
    }


    public void initTextViews(){
        /*  INITIALIZE TEXTVIEWS with TextView references

            There are 3 TextViews, one for displaying the # of hits
            one for the # of misses, and one for displaying the
            countdown to 0.

            Find those TextViews based on their IDs and store them in
            their respective variables for later manipulation

            hits - number of pairs correctly matched
            misses - number of pairs incorrectly matched
            cd - countdown
         */

        hitTextView = (TextView)findViewById(R.id.hitTextView);
        missTextView = (TextView)findViewById(R.id.missTextView);
        cdTextView = (TextView)findViewById(R.id.cdTextView);
    }


    public void countdown(){
        /*
            COUNTDOWN 3 seconds

            Revert the values of the cards back to the default, ???,
            after 3 seconds.

         */

        new CountDownTimer(3000, 1000){
            public void onTick(long millisUntilFinished){
                millisUntilFinished = TimeUnit.MILLISECONDS.toSeconds(
                        millisUntilFinished);
                cdTextView.setText(millisUntilFinished + "");
            }
            public void onFinish(){
                for(int x = 0; x < cards.length; x++){
                    cards[x].setText(R.string.cards_default);
                }
                cdTextView.setText(R.string.default_zero);
            }
        }.start();
    }


    public boolean sameValues() {
        /*  SAME VALUES?

            Checks if pair of cards have matching values. If they do,
            add to the number of hits and display accordingly. If they
            do not add to the number of misses and display accordingly.

            value[0] and value [1] are defined in onCardClick()
         */

        if (value[0] == value[1]) {
            hits++;
            hitTextView.setText(hits +"");
            return true;
        }
        else{
            misses++;
            missTextView.setText(misses +"");
            return false;
        }
    }


    public boolean isEndGame(){
        /* IS ENDGAME?

            Checks if all the matching pairs have been found. If yes,
            return true, if no, return false.

            Function is used in onCardClick();
         */

        for(int i=0; i < cards.length; i++){
            if( cards[i].isEnabled() == false){
                endgame = true;
            }
            else return false;
        }
        return true;
    }


    public void onCardClick(View v){
        /*
            ON CARD CLICK EVENT HANDLER

            When a card is tapped, iterate through Button array "cards".
            If the button tapped matches a button in the array, display
            the tapped card's corresponding value and then increment the
            counter.

            id[] and value[] are trackers that help keep track of the
            cards matched with each other.

            If the counter is 1, store the value of the counter i in id[0]
            and the value of the tapped card in value[0]. Likewise for
            counter 2.

            If the counter is 2, two cards have been tapped. Check if they
            have the same value through the sameValues() function. If they
            match, disable the buttons so that the user will not be able
            to tap them again and bring back the counter to 0. If they do
            not match, on the next card tapped, the counter will become 3,
            satisfying the counter == 3 condition.

            If the counter is 3, meaning the user has incorrectly matched
            two cards and has picked another one. Set the mismatched cards
            back to their default value of ??? and bring back the counter
            to 1 so that the next card tapped will match the previous one.

            Repeat until isEndGame variable is true. When endgame has been
            reached, display via Toast that the user is done with the
            puzzle.

         */

        for (int i = 0; i < cards.length; i++) {
            if (cards[i].getId() == v.getId()) {
                cards[i].setText(iconList.get(i) +"");
                counter++;

                if (counter == 3) {
                    cards[id[0]].setText(R.string.cards_default);
                    cards[id[1]].setText(R.string.cards_default);
                    counter = 1;
                }
                if (counter == 1) {
                    id[0] = i;
                    value[0] = iconList.get(i);
                }
                if (counter == 2) {
                    id[1] = i;
                    value[1] = iconList.get(i);

                    if (sameValues()) {
                        cards[id[0]].setEnabled(false);
                        cards[id[1]].setEnabled(false);
                        counter = 0;
                    }
                }
                // Log.d("counter", counter +""); For debugging purposes
            }
        }
        if (isEndGame() == true){
            Toast.makeText(this, "FINISHED!\nHITS: " +hits +"   " +
                    "MISSES: " +misses, Toast.LENGTH_SHORT).show();
        }
    }
}