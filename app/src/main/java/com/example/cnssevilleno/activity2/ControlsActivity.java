package com.example.cnssevilleno.activity2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class ControlsActivity extends AppCompatActivity {

    // Default controls
    private int millis;
    private int cardValue;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controls);

        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(
                        getApplicationContext());
        editor = sharedPref.edit();

        millis = sharedPref.getInt("millis", 3000);
        cardValue = sharedPref.getInt("cardValue", 3);

        switch(millis){
            case 3000:
                ((RadioButton)findViewById(R.id.count2)).setChecked(true);
                break;
            case 4000:
                ((RadioButton)findViewById(R.id.count3)).setChecked(true);
                break;
            case 5000:
                ((RadioButton)findViewById(R.id.count4)).setChecked(true);
                break;
            case 6000:
                ((RadioButton)findViewById(R.id.count5)).setChecked(true);
                break;
        }

        switch(cardValue){
            case 0:
                ((RadioButton)findViewById(R.id.radio_letters)).setChecked(true);
                break;
            case 1:
                ((RadioButton)findViewById(R.id.radio_numbers)).setChecked(true);
                break;
            case 2:
                ((RadioButton)findViewById(R.id.radio_symbols)).setChecked(true);
                break;
            case 3:
                ((RadioButton)findViewById(R.id.radio_pics)).setChecked(true);
                break;

        }


    }


    public void onRadioButtonClicked(View view) {
        // Check if button was clicked
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_letters:
                if (checked)
                    cardValue = 0;
                    break;
            case R.id.radio_numbers:
                if (checked)
                    cardValue = 1;
                    break;
            case R.id.radio_symbols:
                if (checked)
                    cardValue = 2;
                    break;
            case R.id.radio_pics:
                if (checked)
                    cardValue = 3;
                break;
        }
    }


    public void onRadioButtonClickedTimer(View view) {
         /*
            Check which radio button was clicked and set number of
            milliseconds accordingly. Then pass set milliseconds
            to millis setter method.
        */

        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.count2:
                if (checked)
                    millis = 3000;
                    break;
            case R.id.count3:
                if (checked)
                    millis = 4000;
                    break;
            case R.id.count4:
                if (checked)
                    millis = 5000;
                    break;
            case R.id.count5:
                if (checked)
                    millis = 6000;
                    break;
        }
    }


    public void saveConfigurations(View v){
        editor.putInt("millis", millis);
        editor.putInt("cardValue", cardValue);
        editor.commit();

        Toast.makeText(this, "Controls saved!", Toast.LENGTH_SHORT).show();
    }
}
