package com.example.tictactoe;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import java.util.Random;

public class BotActivity extends AppCompatActivity {


    int playerNo=0;
    int[][] winningPositions={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    //0 : human player , 1 : bot player and 2 : empty
    int[] gameState={2,2,2,2,2,2,2,2,2};

    boolean gameWon=false;
    boolean gameTie=false;
    GridLayout grid;
    int tapPosition;

    //depth of minimax algorithm change difficulty of bot with this variable
    int depth=4;
    int alpha=-1000;
    int beta=1000;
    Button playAgainButton;
    TextView messageTextView;
    String winner;
    Intent intent;
    boolean botIsO;
    boolean xPlaysFirst;

    TextView xScoreTextView,yScoreTextView,playerXnameTextView,playerOnameTextView;

    int xScore=0;
    int yScore=0;

    public void humanTurn(View view)
    {
        ImageView x=(ImageView) view;

        tapPosition=Integer.parseInt(x.getTag().toString());

        //check if position is empty and check whether game is over
        if(gameState[tapPosition]==2&& !gameWon) {
            gameState[tapPosition] = playerNo;


            if (playerNo == 0) {
                if(botIsO)
                {
                    dropIn(x,"X");
                }
                else
                    {
                        dropIn(x,"O");
                    }
                playerNo = 1;
            }

            if(checkIfGameWon())
            {   showWinner(); }
            else
                {
                    botTurn();
                }
        }
    }

    public void botTurn() {

        int bestMove=0;
        int bestScore=-1000;
        int score;
        for(int i=0;i<=8;i++)
        {
            if (gameState[i]==2)
            {   //changing gamestate variable temporarily for proper functioning of minimax algorithm
                gameState[i]=1;
                score=miniMax(gameState,depth, alpha,beta,false);
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
        if(botIsO)
        {
            dropIn(imageView,"O");
        }
        else
            {
                dropIn(imageView,"X");
            }

        gameState[bestMove]=playerNo;

        if(checkIfGameWon())
        { showWinner(); }
        playerNo = 0;

    }

    public void dropIn(ImageView imageView , String xOrO )
    {  imageView.setTranslationY(-1500);
        if(xOrO.equals("X"))
        {
            imageView.setImageResource(R.drawable.stylishx);
        }
        else
            {
                imageView.setImageResource(R.drawable.stylisho);
            }
        imageView.animate().translationYBy(1500).rotation(720).setDuration(700);

    }

    //scores X:-10, O:10 ,tie :0

    public int miniMax(int[] gameState, int depth,int alpha,int beta, boolean isMaximizingPlayer) {
        boolean gameOver=checkIfGameWon();
        if(depth==0 || gameOver)
        {
            if(winner!=null){
                if(winner.equals("X") && botIsO)
                {   winner=null;
                    gameWon=false;
                    gameTie=false;
                    return -10-depth;}
                else if(winner.equals("X") && !botIsO)
                {   winner=null;
                    gameWon=false;
                    gameTie=false;
                    return 10+depth;
                }
                else if(winner.equals("O") && botIsO)
                {   winner=null;
                    gameWon=false;
                    gameTie=false;
                    return 10+depth;}
                else if(winner.equals("O") && !botIsO)
                {    winner=null;
                    gameWon=false;
                    gameTie=false;
                    return -10-depth;
                }
            }
           else {gameTie=false; return 0;}
        }

        if(isMaximizingPlayer)
        {   int maxScore=-1000;
            for(int i=0;i<=8;i++)
            {
                if(gameState[i]==2)
                {
                    gameState[i]=1;
                    int score=miniMax(gameState,depth-1, alpha,beta,false);
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
                int minScore=1000;
                for(int i=0;i<=8;i++)
                {
                    if(gameState[i]==2)
                    {
                        gameState[i]=0;
                        int score=miniMax(gameState,depth-1,alpha,beta, true);
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
                    if(botIsO)
                    {
                        winner = "X";
                    } else
                        {
                            winner = "O";
                        }

                } else {
                    if(botIsO)
                    {
                        winner = "O";
                    } else
                        {
                            winner = "X";
                    }
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
    {       if(gameWon)
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
            } else
                {   if(gameTie)
                    { messageTextView.setText(R.string.stalemateMessage);
                    }
                }
        playAgainButton.setVisibility(View.VISIBLE);
    }

    public void playAgain(View view)
    {   //reset the game
        playAgainButton.setVisibility(View.INVISIBLE);
        messageTextView.setText("");
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

        botRandomTurn();
    }
    public void botRandomTurn()
    {
        if(xPlaysFirst && !botIsO)
        {
            Random random=new Random();
            int randomNum=random.nextInt(9);
            gameState[randomNum]=1;
            dropIn((ImageView) grid.getChildAt(randomNum),"X");
        } else if(!xPlaysFirst && botIsO)
        {   Random random=new Random();
            int randomNum=random.nextInt(9);
            gameState[randomNum]=1;
            dropIn((ImageView) grid.getChildAt(randomNum),"O");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        playAgainButton= findViewById(R.id.playAgainButton);
        messageTextView= findViewById(R.id.gameStatusTextView);
        grid= findViewById(R.id.gridLayout);
        xScoreTextView=findViewById(R.id.xScoreTextView);
        playerXnameTextView=findViewById(R.id.player1NameTextView);
        playerOnameTextView=findViewById(R.id.player2NameTextView);
        yScoreTextView=findViewById(R.id.oScoreTextView);


        intent=getIntent();
        botIsO=intent.getBooleanExtra("botIsO",true);
        if(botIsO)
        {
            playerXnameTextView.setText("Human");
            playerOnameTextView.setText("Computer");
        } else
            {
                playerOnameTextView.setText("Human   ");
                playerXnameTextView.setText("Computer");
            }
        xPlaysFirst=intent.getBooleanExtra("xPlaysFirst",true);

        botRandomTurn();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void mainMenu(View view) {
        finish();
    }
}