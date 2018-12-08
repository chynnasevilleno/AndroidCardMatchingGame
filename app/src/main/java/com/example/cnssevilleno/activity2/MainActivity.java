package com.example.cnssevilleno.activity2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void launchSecondActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }


    public void launchControlsActivity(View view) {
        Intent intent = new Intent(this, ControlsActivity.class);
        startActivity(intent);
    }


    public void exitApplication(View view) {
        finish();
        System.exit(0);
    }
}
