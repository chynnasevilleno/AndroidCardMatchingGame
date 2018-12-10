package com.example.cnssevilleno.activity2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.media.MediaPlayer;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    // Songs
    private MediaPlayer mememachine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        mememachine = MediaPlayer.create(this, R.raw.mememachine);
        mememachine.start();
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

    @Override
    protected void onPause() {
        super.onPause();

        mememachine.stop();
    }
}
