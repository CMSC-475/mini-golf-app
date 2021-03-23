package com.gooey_group.minigolfapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TableLayout;
import android.widget.TableRow;
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
            tableSize = gameData.numPlayers - 1;

            // sort players based on total score
            Arrays.sort(gameData.players);

            // check for certain cases so the app screen is formatted better
            if (gameData.numPlayers == 1) {
                TableLayout playersTable = (TableLayout) findViewById(R.id.table1);
                playersTable.setVisibility(View.GONE);
                Player playerOne = gameData.players[0];
                TextView playerOneName = (TextView) findViewById(R.id.name1);
                TextView playerOneScore = (TextView) findViewById(R.id.score1);
                playerOneName.setText(playerOne.name);
                playerOneScore.setText("Score: " + Integer.toString(playerOne.totalScore));
            }
            else if (gameData.numPlayers == 2) {
                // todo: hard coded table for now, will make dynamic later
                // can possibly just put this in a for loop, with i being used to find gameData.players[i], name(i+1), and score(i+1)
                Player playerOne = gameData.players[0];
                TextView playerOneName = (TextView) findViewById(R.id.name1);
                TextView playerOneScore = (TextView) findViewById(R.id.score1);
                playerOneName.setText(playerOne.name);
                playerOneScore.setText("Score: " + Integer.toString(playerOne.totalScore));

                Player playerTwo = gameData.players[1];
                TextView playerTwoName = (TextView) findViewById(R.id.name2);
                TextView playerTwoScore = (TextView) findViewById(R.id.score2);
                playerTwoName.setText("2. " + playerTwo.name);
                playerTwoScore.setText(Integer.toString(playerTwo.totalScore));
                TableRow removeRow = (TableRow) findViewById((R.id.row2));
                removeRow.setVisibility(View.GONE);
                removeRow = (TableRow) findViewById((R.id.row3));
                removeRow.setVisibility(View.GONE);
            }
            // currently only supports up to four players
            else {
                Player currPlayer = gameData.players[2];
                TextView currPlayerName = (TextView) findViewById(R.id.name3);
                TextView currPlayerScore = (TextView) findViewById(R.id.score3);
                currPlayerName.setText("3. " + currPlayer.name);
                currPlayerScore.setText(Integer.toString(currPlayer.totalScore));

                currPlayer = gameData.players[3];
                currPlayerName = (TextView) findViewById(R.id.name4);
                currPlayerScore = (TextView) findViewById(R.id.score4);
                currPlayerName.setText("4. " + currPlayer.name);
                currPlayerScore.setText(Integer.toString(currPlayer.totalScore));
            }
        }


        homepageButton = findViewById(R.id.returnHP);
        homepageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EndGameActivity.this, SelectActivity.class);
                startActivity(intent);
            }
        });



    }
}