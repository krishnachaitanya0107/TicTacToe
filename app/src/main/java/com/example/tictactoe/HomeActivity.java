package com.example.tictactoe;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    public void play(View view)
    {   int gameMode=view.getId();
        Intent intent=new Intent(this, SettingsActivity.class);
        if(gameMode==R.id.playHumanButton)
        {
            intent.putExtra("userChoice","humanMode");
         }
        else {
            intent.putExtra("userChoice","botMode");
             }
         startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
