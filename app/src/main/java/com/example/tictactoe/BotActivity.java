package com.example.tictactoe;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

public class BotActivity extends AppCompatActivity {



    int playerNo=0;
    int[][] winningPositions={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    //0 : player x , 1 : player o and 2 : empty
    //0 : human player , 1 : bot player and 2 : empty
    int[] gameState={2,2,2,2,2,2,2,2,2};

    boolean gameWon=false;
    boolean gameTie=false;
    GridLayout grid;
    int tapPosition;

    //depth of minimax algorithm change difficulty of bot with this variable
    int depth=4;
    int alpha=-100;
    int beta=100;
    Button playAgainButton;
    TextView messageTextView;
    String winner;

    TextView xScoreTextView;
    TextView yScoreTextView;

    int xScore=0;
    int yScore=0;

    public void dropIn(View view)
    {
        ImageView x=(ImageView) view;

        tapPosition=Integer.parseInt(x.getTag().toString());

        //check if position is empty and check whether game is over
        if(gameState[tapPosition]==2&& !gameWon) {
            gameState[tapPosition] = playerNo;
            x.setTranslationY(-1500);

            if (playerNo == 0) {
                x.setImageResource(R.drawable.stylishx);
                messageTextView.setText(R.string.botTurn);
                playerNo = 1;
            }

            x.animate().translationYBy(1500).rotation(720).setDuration(700);

            if(checkIfGameWon())
            { showWinner(); }
            else
                {   messageTextView.setText(R.string.humanTurn);
                    botTurn();
                }
        }
    }

    public void botTurn() {

        int bestMove=0;
        int bestScore=-100;
        int score;
        for(int i=0;i<=8;i++)
        {
            if (gameState[i]==2)
            {   //changing gamestate variable temporarily for proper functioning of minimax algorithm
                gameState[i]=1;
                score=miniMax(gameState,depth,alpha,beta,false);
                // resetting gamestate variable
                gameState[i]=2;
                if(score>bestScore)
                {
                    bestScore=score;
                    bestMove=i;
                }
            }
        }

        //bot plays its turn
        ImageView imageView=(ImageView) grid.getChildAt(bestMove);
        imageView.setTranslationY(-1500);
        imageView.setImageResource(R.drawable.stylisho);
        imageView.animate().translationYBy(1500).rotation(720).setDuration(700);

        gameState[bestMove]=playerNo;

        if(checkIfGameWon())
        { showWinner(); }
        playerNo = 0;

    }

    //scores X:-1, O:1 ,tie :0

    public int miniMax(int[] gameState, int depth,int alpha,int beta, boolean isMaximizingPlayer) {
        boolean gameOver=checkIfGameWon();
        if(depth==0 || gameOver)
        {
            if(winner!=null){
                gameWon=false;
                gameTie=false;
                if(winner.equals("X"))
                {   winner=null;
                    return -1;}
                else if(winner.equals("O"))
                {   winner=null;
                    return 1;}
            }
           else {gameTie=false; return 0;}
        }

        if(isMaximizingPlayer)
        {   int maxScore=-100;
            for(int i=0;i<=8;i++)
            {
                if(gameState[i]==2)
                {
                    gameState[i]=1;
                    int score=miniMax(gameState,depth-1,alpha,beta,false);
                    gameState[i]=2;
                    maxScore=Math.max(score,maxScore);
                    alpha=Math.max(alpha,score);
                    if(beta<=alpha)
                    {
                        break;
                    }
                }
            }
            return maxScore;
        }
        else
            {
                int minScore=100;
                for(int i=0;i<=8;i++)
                {
                    if(gameState[i]==2)
                    {
                        gameState[i]=0;
                        int score=miniMax(gameState,depth-1,alpha,beta,true);
                        gameState[i]=2;
                        minScore=Math.min(score,minScore);
                        beta=Math.min(beta,score);
                        if(beta<=alpha)
                        {
                            break;
                        }
                    }
                }
                return minScore;
            }
    }

    public boolean checkIfGameWon()
    {
        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]]
                    && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                    && gameState[winningPosition[0]] != 2) {
                gameWon=true;
                if (gameState[winningPosition[0]] == 0) {
                    winner = "X";
                } else {
                    winner = "O";
                }
            } else if (!gameWon){
                int found = 0;
                for (int n : gameState) {
                    if (n==2)
                        found++; }
                if(found==0)
                {   gameTie=true; }
            }
        }
        return gameTie || gameWon;
    }

    public void showWinner()
    {    if(gameTie)
        { messageTextView.setText(R.string.stalemateMessage); }
        else
            { messageTextView.setText(String.format("%s has won ", winner));
                if(winner.equals("X"))
                {
                    xScore=xScore+1;
                    xScoreTextView.setText(Integer.toString(xScore));
                }
                if(winner.equals("O"))
                {
                    yScore=yScore+1;
                    yScoreTextView.setText(Integer.toString(yScore));
                }
            }
        playAgainButton.setVisibility(View.VISIBLE);
    }

    public void playAgain(View view)
    {   //reset the game
        playAgainButton.setVisibility(View.INVISIBLE);
        messageTextView.setText(R.string.humanTurn);
        for(int i=0;i<grid.getChildCount();i++)
        {
            ImageView counter=(ImageView)grid.getChildAt(i);
            counter.setImageDrawable(null);
        }
        playerNo=0;

        //0 :player x , 1 : player o and 2 : empty

        for (int d=0;d<=8;d++){gameState[d]=2;}
        gameWon=false;
        gameTie=false;
        winner=null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        playAgainButton= findViewById(R.id.button2);
        messageTextView= findViewById(R.id.textView);
        grid= findViewById(R.id.gridLayout);
        xScoreTextView=findViewById(R.id.textView3);
        yScoreTextView=findViewById(R.id.textView4);
        messageTextView.setText(R.string.humanTurn);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void mainMenu(View view) {
        finish();
    }
}