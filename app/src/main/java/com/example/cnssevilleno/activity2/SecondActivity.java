package com.example.cnssevilleno.activity2;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
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
    private Button cards[];
    private int[] pictureList;
    private String[] stringList;
    private ArrayList<String> characterList = new ArrayList<>();
    private ArrayList<Integer> iconList = new ArrayList<>();
    // Controls
    private int cardValue;
    private int millis;
    // Counters
    private int hits = 0;
    private int misses = 0;
    private int counter = 0;
    // Trackers
    private int[] id = new int[2];
    private int[] value_icon = new int[2];
    private String[] value_character = new String[2];
    // TextViews
    private TextView hitTextView;
    private TextView missTextView;
    private TextView cdTextView;
    // Booleans
    private boolean endgame;
    private boolean pictureCardValue = false;
    // Songs
    private MediaPlayer ohyeah;
    private MediaPlayer badtitanic;
    // Countdown Timer
    private CountDownTimer mismatch_countdown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // MAIN FUNCTION fired on activity startup

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Get controls
        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(
                        getApplicationContext());

        millis = sharedPref.getInt("millis", 3000);
        cardValue = sharedPref.getInt("cardValue", 3);

        // Initialize elements
        ohyeah = MediaPlayer.create(this, R.raw.ohyeah);
        badtitanic = MediaPlayer.create(this, R.raw.badtitanic);
        badtitanic.setLooping(true);

        Resources res = getResources();
        switch(cardValue) {
            case 0: // Letters
                stringList =  res.getStringArray(R.array.cv_letters);
                break;
            case 1: // Numbers
                stringList =  res.getStringArray(R.array.cv_numbers);
                break;
            case 2: // Symbols
                stringList =  res.getStringArray(R.array.cv_symbols);
                break;
            case 3: // Pictures
                pictureCardValue = true;
                pictureList = new int[] {
                        R.drawable.one,
                        R.drawable.two,
                        R.drawable.three,
                        R.drawable.four,
                        R.drawable.five,
                        R.drawable.six,
                        R.drawable.seven,
                        R.drawable.eight
                };
                break;
        }

        if (pictureCardValue)
            initIconList(pictureList);
        else
            initStringList(stringList);

        initCards();
        initTextViews();

        // Begin countdown of 3 seconds
        countdown();
    }

    /* INITIALIZE ICON/STRING LIST with 16 shuffled numbers/symbols/
       letters/drawables (pictures)

       iconList is an arraylist that contains the values for the
       16 cards. The number of cards divided by 2 (16/2 = 8) will
       be the number of pairs (8). Numbers 1 - 8 exist twice in
       this arraylist, totaling 16 elements.

       The list is shuffled to have the placement of cards
       randomized each game .
    */

    private void initIconList(int valueList[]){
        for (int y = 0; y < (valueList.length); y++) {
            iconList.add(valueList[y]);
            iconList.add(valueList[y]);
        }
        Collections.shuffle(iconList);
    }


    private void initStringList(String valueList[]){
        for (int y = 0; y < (valueList.length); y++) {
            characterList.add(valueList[y]);
            characterList.add(valueList[y]);
        }
        Collections.shuffle(characterList);
    }


    private void initCards(){
        /*  INITIALIZE CARDS with button references

            There are 16 cards laid out in a 4x4 grid.
            Each card references a button element in the layout

            Get the card's referenced button using the button's ID and
            store them in their respective array indexes for later
            manipulation. Display their respective values and disable
            the cards until the countdown to 0 from x is complete.
         */

        cards = new Button[]{
                (Button)findViewById(R.id.button1),
                (Button)findViewById(R.id.button2),
                (Button)findViewById(R.id.button3),
                (Button)findViewById(R.id.button4),
                (Button)findViewById(R.id.button5),
                (Button)findViewById(R.id.button6),
                (Button)findViewById(R.id.button7),
                (Button)findViewById(R.id.button8),
                (Button)findViewById(R.id.button9),
                (Button)findViewById(R.id.button10),
                (Button)findViewById(R.id.button11),
                (Button)findViewById(R.id.button12),
                (Button)findViewById(R.id.button13),
                (Button)findViewById(R.id.button14),
                (Button)findViewById(R.id.button15),
                (Button)findViewById(R.id.button16)
        };

        for(int x = 0; x < cards.length; x++){
            if (pictureCardValue)
                cards[x].setBackgroundResource(iconList.get(x));
            else
                cards[x].setText(characterList.get(x));
            cards[x].setEnabled(false);
        }
    }


    private void initTextViews(){
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


    private void countdown(){
        /*
            COUNTDOWN x seconds
            Revert the values of the cards back to their defualt value
            after x seconds. x is user defined via the controls.

            TIMER
            After countdown to x seconds is finished, start timer. Once
            user completes puzzle, stop timer, and play music.

         */

        new CountDownTimer(millis, 1000){
            public void onTick(long millisUntilFinished){
                millisUntilFinished = TimeUnit.MILLISECONDS.toSeconds(
                        millisUntilFinished);
                cdTextView.setText(millisUntilFinished + "");
            }


            public void onFinish(){
                // Enable buttons and reset to default values
                for(int x = 0; x < cards.length; x++){
                    if (pictureCardValue)
                        cards[x].setBackgroundResource(R.drawable.button_custom);
                    else
                        cards[x].setText(R.string.cards_default);
                    cards[x].setEnabled(true);
                    badtitanic.start();
                }

                // Start Timer
                final Handler h = new Handler();
                h.postDelayed(new Runnable()
                {
                    private long time = 0;

                    @Override
                    public void run()
                    {
                        time += 1000;
                        int seconds = (int) (time / 1000);
                        int minutes = seconds / 60;
                        seconds = seconds % 60;

                        cdTextView.setText(String.format(
                                "%d:%02d", minutes, seconds));
                        h.postDelayed(this, 1000);

                        if (isEndGame()){
                            h.removeCallbacks(this);
                            Toast.makeText(getApplicationContext(),
                                    "Hey b0ss, you finished!",
                                    Toast.LENGTH_SHORT).show();

                            badtitanic.stop();
                            ohyeah.start();
                        }
                    }
                }, 1000); // 1 second delay (takes millis)
            }
        }.start();
    }


    private boolean sameValues() {
        /*  SAME VALUES?

            Checks if pair of cards have matching values. If they do,
            add to the number of hits and display accordingly. If they
            do not add to the number of misses and display accordingly.

            value[0] and value [1] are defined in onCardClick()
         */

        if (pictureCardValue) {
            if (value_icon[0] == value_icon[1]) {
                hits();
                return true;
            }
            else{
                misses();
                return false;
            }
        }
        else{
            if (value_character[0] == value_character[1]) {
                hits();
                return true;
            }
            else{
                misses();
                return false;
            }
        }
    }


    public void hits(){
        hits++;
        hitTextView.setText(hits + "");

    }


    public void misses(){
        misses++;
        missTextView.setText(misses + "");
    }


    private boolean isEndGame(){
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


    private void revert_pair(int first_card, int second_card){
        // Reverts a pair of user chosen cards back to their defaults

        cards[first_card].setBackgroundResource(R.drawable.button_custom);
        cards[second_card].setBackgroundResource(R.drawable.button_custom);

        if (! pictureCardValue){
            cards[first_card].setText(R.string.cards_default);
            cards[second_card].setText(R.string.cards_default);
        }
    }


    private void enable_pair(int first_card, int second_card){
        // Enables a pair of user chosen cards

        cards[first_card].setEnabled(true);
        cards[second_card].setEnabled(true);
    }


    private void disable_pair(int first_card, int second_card){
        // Disables a pair of user chosen cards

        if (!pictureCardValue){
            cards[first_card].setBackgroundColor(Color.GREEN);
            cards[second_card].setBackgroundColor(Color.GREEN);
        }


        cards[first_card].setEnabled(false);
        cards[second_card].setEnabled(false);
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
                if (pictureCardValue)
                    cards[i].setBackgroundResource(iconList.get(i));
                else
                    cards[i].setText(characterList.get(i));

                if (cards[i].isEnabled()){
                    cards[i].setEnabled(false);
                }
                counter++;

                if (counter == 3) {
                    mismatch_countdown.cancel();
                    revert_pair(id[0], id[1]);
                    counter = 1;
                }
                if (counter == 1) {
                    id[0] = i;
                    if (pictureCardValue)
                        value_icon[0] = iconList.get(i);
                    else
                        value_character[0] = characterList.get(i);
                }
                if (counter == 2) {
                    id[1] = i;
                    if (pictureCardValue)
                        value_icon[1] = iconList.get(i);
                    else
                        value_character[1] = characterList.get(i);

                    if (sameValues()) {
                        disable_pair(id[0], id[1]);
                        counter = 0;
                    }
                    else {
                        enable_pair(id[0], id[1]);
                        mismatch_countdown = new CountDownTimer(2000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                if (!pictureCardValue){
                                    cards[id[0]].setBackgroundColor(Color.RED);
                                    cards[id[1]].setBackgroundColor(Color.RED);
                                }
                            }

                            public void onFinish() {
                                revert_pair(id[0], id[1]);
                                counter = 0;
                            }
                        }.start();
                    }
                }
                Log.d("counter", counter +""); // For debugging purposes
            }
        }
    }


    @Override
    protected void onPause() {
        ohyeah.stop();
        badtitanic.pause();
        super.onPause();
    }
}