package com.example.cnssevilleno.activity2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

public class ControlsActivity extends AppCompatActivity {

    public int millis = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controls);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_letters:
                if (checked)
                    // Use letters array (?) in SecondActivity
                    break;
            case R.id.radio_numbers:
                if (checked)
                    // Use numbers array in SecondActivity
                    break;
            case R.id.radio_pics:
                if (checked)
                    // Use pics array in SecondActivity
                    break;
            case R.id.radio_symbols:
                if (checked)
                    // Use symbols array in SecondActivity (it's there na but idk how to trigger it. could be boolean?)
                    break;
        }
    }

    public void onRadioButtonClickedTimer(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.count2:
                if (checked)
                    // Pass to SecondActivity as 3000 milliseconds
                    millis = 3000;
                    break;
            case R.id.count3:
                if (checked)
                    // Pass to SecondActivity as 4000 milliseconds
                    millis = 4000;
                    break;
            case R.id.count4:
                if (checked)
                    // Pass to SecondActivity as 5000 milliseconds
                    millis = 5000;
                    break;
            case R.id.count5:
                if (checked)
                    // Pass to SecondActivity as 6000 milliseconds
                    millis = 000;
                    break;
        }

    }

    public void saveConfigurations(View view) {
        //Pass to SecondActivity configurations
    }
}
