package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    String mode;
    boolean xPlaysFirst=true;
    boolean botIsO=true;
    Switch s;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        s=findViewById(R.id.switch1);
        checkBox=findViewById(R.id.xPlaysFirst);
        Intent intent=getIntent();
        mode=intent.getStringExtra("userChoice");
        if (mode != null && mode.equals("humanMode")) {
            s.setEnabled(false);
        }
    }

    public void playNow(View view) {
        if(!checkBox.isChecked())
        {
            xPlaysFirst=false;
        }
        if(!s.isChecked())
        {
            botIsO=false;
        }
        Intent intent;

        if( mode.equals("botMode"))
        {
            intent=new Intent(SettingsActivity.this,BotActivity.class);
            intent.putExtra("botIsO",botIsO);
        }
        else
            {
                intent=new Intent(SettingsActivity.this,PlayerActivity.class);
            }

        intent.putExtra("xPlaysFirst",xPlaysFirst);
        startActivity(intent);
    }
}