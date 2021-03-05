package com.gooey_group.minigolfapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;

import java.util.Arrays;

import java.io.Serializable;

public class EndGameActivity extends AppCompatActivity implements Serializable {

    Button homepageButton;
    int tableSize;
    Intent gameIntent;
    Game gameData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        // intent that receives an intent of type Game
        if (getIntent().getSerializableExtra("GameInfo") != null) {
            gameData = (Game) getIntent().getSerializableExtra("GameInfo");
            //gameData = (Game) gameIntent.getSerializableExtra("boardname");
            tableSize = gameData.numPlayers - 1;

            // sort players based on total score
            //Arrays.sort(gameData.players);

            Player playerOne = gameData.players[0];
            TextView playerOneName = (TextView) findViewById(R.id.name1);
            TextView playerOneScore = (TextView) findViewById(R.id.score1);
            playerOneName.setText(playerOne.name);
            playerOneScore.setText("Score: " + Integer.toString(playerOne.totalScore));

            if (gameData.numPlayers == 4) {
                // todo: hard coded table for now, will make dynamic later
                // can possibly just put this in a for loop, with i being used to find gameData.players[i], name(i+1), and score(i+1)
                Player playerTwo = gameData.players[1];
                TextView playerTwoName = (TextView) findViewById(R.id.name2);
                TextView playerTwoScore = (TextView) findViewById(R.id.score2);
                playerTwoName.setText(playerTwo.name);
                playerTwoScore.setText(Integer.toString(playerTwo.totalScore));

                Player playerThree = gameData.players[2];
                TextView playerThreeName = (TextView) findViewById(R.id.name3);
                TextView playerThreeScore = (TextView) findViewById(R.id.score3);
                playerThreeName.setText(playerTwo.name);
                playerThreeScore.setText(Integer.toString(playerThree.totalScore));

                Player playerFour = gameData.players[3];
                TextView playerFourName = (TextView) findViewById(R.id.name4);
                TextView playerFourScore = (TextView) findViewById(R.id.score4);
                playerFourName.setText(playerTwo.name);
                playerFourScore.setText(Integer.toString(playerFour.totalScore));
            }
        }


        homepageButton = findViewById(R.id.returnHP);
        homepageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EndGameActivity.this, NewGameActivity.class);
                startActivity(intent);
            }
        });



    }
}