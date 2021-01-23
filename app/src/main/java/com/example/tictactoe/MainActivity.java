package com.example.tictactoe;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

public class MainActivity extends AppCompatActivity {

    int playerNo=0;
    int[][] winningPositions={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    //0 : player x , 1 : player o and 2 : empty
    int[] gameState={2,2,2,2,2,2,2,2,2};
    boolean gameWon=false;
    int tapPosition;
    Button play;
    TextView win;
    String winner;
    String staleMateMessage;

    TextView xTextView;
    TextView yTextView;
    
    int xScore=0;
    int yScore=0;
    
    public void dropIn(View view)
    {
        ImageView x=(ImageView) view;

        tapPosition=Integer.parseInt(x.getTag().toString());
        if(gameState[tapPosition]==2&& !gameWon) {
            gameState[tapPosition] = playerNo;
            x.setTranslationY(-1500);

            if (playerNo == 0) {
                x.setImageResource(R.drawable.stylishx);
                win.setText(R.string.oTurn);
                playerNo = 1;
            } else {
                x.setImageResource(R.drawable.stylisho);
                win.setText(R.string.xTurn);
                playerNo = 0;
            }

            x.animate().translationYBy(1500).rotation(7200).setDuration(700);

            checkForWinner();
        }
        }

    private void checkForWinner() {
        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]]
                    && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                    && gameState[winningPosition[0]] != 2) {
                gameWon=true;
                if (playerNo == 1) {
                    winner = "Player X";
                    xScore=xScore+1;
                    xTextView.setText(Integer.toString(xScore));
                } else {
                    yScore=yScore+1;
                    winner = "Player O";
                    yTextView.setText(Integer.toString(yScore));
                }

                play.setVisibility(View.VISIBLE);
                win.setText(String.format("%s has won ", winner));

            } else if (!gameWon){
                int found = 0;
                for (int n : gameState) {
                    if (n==2)
                        found++; }
                if(found==0)
                {   play.setVisibility(View.VISIBLE);
                    win.setText(staleMateMessage);
                }
            }
        }
    }

    public void playAgain(View view)
        {   //reset all variables
            play.setVisibility(View.INVISIBLE);
            win.setText(R.string.xTurn);
            GridLayout grid= findViewById(R.id.gridLayout);
            for(int i=0;i<grid.getChildCount();i++)
            {
                ImageView counter=(ImageView)grid.getChildAt(i);
                counter.setImageDrawable(null);

            }
            playerNo=0;

            //0 :player x , 1 : player o and 2 : empty
            for (int d=0;d<=8;d++){gameState[d]=2;}
            gameWon=false;
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        staleMateMessage="StaleMate , it was a draw";
        play= findViewById(R.id.button2);
        win= findViewById(R.id.textView);
        win.setText(R.string.xTurn);
        xTextView=findViewById(R.id.textView3);
        yTextView=findViewById(R.id.textView4);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void mainMenu(View view) {
        finish();
    }
}
