package com.gooey_group.minigolfapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
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
    Game gameData;
    int totalPlayers;
    int currentPlayerScore = 0;
    String currentPlayerName = "";
    Player currentPlayerInfo;
    TextView currentPlayerNameView;
    TextView currentPlayerScoreView;
    final int MAX_SCORE = 999;


    // TODO: improved dynamic table, table shadow, scrollable table
    // make headers consistent, main page button not centered, make buttons same sizes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        // intent that receives an intent of type Game
        if (getIntent().getSerializableExtra("GameInfo") != null) {
            gameData = (Game) getIntent().getSerializableExtra("GameInfo");
            //testGameInstance();
            tableSize = gameData.numPlayers;
            totalPlayers = gameData.numPlayers;
            // sort players based on total score
            Arrays.sort(gameData.players);
            if (totalPlayers <= 0) {
                TableLayout playersTable = (TableLayout) findViewById(R.id.table1);
                playersTable.setVisibility(View.GONE);
                currentPlayerNameView = (TextView) findViewById(R.id.name1);
                currentPlayerScoreView = (TextView) findViewById(R.id.score1);
                currentPlayerNameView.setText("No One!");
                currentPlayerScoreView.setText("0");
            }
            // check for certain cases so the app screen is formatted better
            else if (totalPlayers == 1) {
                TableLayout playersTable = (TableLayout) findViewById(R.id.table1);
                playersTable.setVisibility(View.GONE);
                currentPlayerInfo = gameData.players[0];
                currentPlayerNameView = (TextView) findViewById(R.id.name1);
                currentPlayerScoreView = (TextView) findViewById(R.id.score1);
                currentPlayerName = currentPlayerInfo.name;
                if (currentPlayerName.length() > 12) {
                    currentPlayerName = currentPlayerName.substring(0, 12) + ".";
                }
                currentPlayerNameView.setText(currentPlayerName);
                currentPlayerScore = currentPlayerInfo.totalScore;
                if (currentPlayerScore > MAX_SCORE) {
                    currentPlayerScore = MAX_SCORE;
                }
                currentPlayerScoreView.setText("Score: " + Integer.toString(currentPlayerScore));
            }
            else {
                TableLayout playersTable = (TableLayout) findViewById(R.id.table1);
                currentPlayerInfo = gameData.players[0];
                currentPlayerNameView = (TextView) findViewById(R.id.name1);
                currentPlayerName = currentPlayerInfo.name;
                if (currentPlayerName.length() > 12) {
                    currentPlayerName = currentPlayerName.substring(0, 12) + ".";
                }
                currentPlayerScoreView = (TextView) findViewById(R.id.score1);
                currentPlayerNameView.setText(currentPlayerName);
                currentPlayerScore = currentPlayerInfo.totalScore;
                if (currentPlayerScore > MAX_SCORE) {
                    currentPlayerScore = MAX_SCORE;
                }
                currentPlayerScoreView.setText("Score: " + Integer.toString(currentPlayerScore));

                int playerPosition = 2;
                String playerPositionAndName = "";
                for(int i = 1; i < tableSize; i++) {
                    TableRow tr =  new TableRow(this);
                    TextView playerNameText = new TextView(this);
                    playerNameText.setTextSize(26);
                    currentPlayerInfo = gameData.players[i];
                    currentPlayerName = currentPlayerInfo.name;
                    if (currentPlayerName.length() > 12) {
                        currentPlayerName = currentPlayerName.substring(0, 12) + ".";
                    }

                    playerPositionAndName = " " + Integer.toString(playerPosition) + ". " + currentPlayerName;
                    playerPosition++;
                    playerNameText.setText(playerPositionAndName);
                    //playerNameText.setHeight(150);
                    playerNameText.setBackgroundResource(R.color.white);
                    tr.addView(playerNameText);
                    TextView playerScoreText = new TextView(this);
                    playerScoreText.setTextSize(26);
                    currentPlayerScore = currentPlayerInfo.totalScore;
                    if (currentPlayerScore > MAX_SCORE) {
                        currentPlayerScore = MAX_SCORE;
                    }
                    playerScoreText.setText(Integer.toString(currentPlayerScore));
                    playerScoreText.setGravity(Gravity.CENTER);
                    playerScoreText.setBackgroundResource(R.color.white);
                    tr.addView(playerScoreText);
                    playersTable.addView(tr);
                }
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

    // Testing purposes only
    public void testGameInstance() {
        Player playerOne = new Player(9);
        playerOne.totalScore = 5;
        playerOne.setName("Joe");
        Player playerTwo = new Player(9);
        playerTwo.totalScore = 6;
        playerTwo.setName("John");
        Player playerThree = new Player(9);
        playerThree.totalScore = 7;
        playerThree.setName("Andy");
        gameData = new Game(3, 9);
        gameData.players[0] = playerOne;
        gameData.players[1] = playerTwo;
        gameData.players[2] = playerThree;
    }
}