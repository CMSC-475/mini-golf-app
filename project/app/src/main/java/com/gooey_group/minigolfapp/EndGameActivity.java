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
    Player currentPlayer;
    TextView currentPlayerName;
    TextView currentPlayerScore;


    // TODO: tie condition, improved dynamic table, table shadow, scrollable table
    // TODO: error handling (no players, etc)
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

            // check for certain cases so the app screen is formatted better
            if (totalPlayers == 1) {
                TableLayout playersTable = (TableLayout) findViewById(R.id.table1);
                playersTable.setVisibility(View.GONE);
                currentPlayer = gameData.players[0];
                TextView currentPlayerName = (TextView) findViewById(R.id.name1);
                TextView currentPlayerScore = (TextView) findViewById(R.id.score1);
                currentPlayerName.setText(currentPlayer.name);
                currentPlayerScore.setText("Score: " + Integer.toString(currentPlayer.totalScore));
            }
            else {
                TableLayout playersTable = (TableLayout) findViewById(R.id.table1);
                currentPlayer = gameData.players[0];
                TextView currentPlayerName = (TextView) findViewById(R.id.name1);
                TextView currentPlayerScore = (TextView) findViewById(R.id.score1);
                currentPlayerName.setText(currentPlayer.name);
                currentPlayerScore.setText("Score: " + Integer.toString(currentPlayer.totalScore));

                int playerPosition = 2;
                String playerPositionAndName = "";
                String currentName = "";
                for(int i = 1; i < tableSize; i++) {
                    TableRow tr =  new TableRow(this);
                    TextView playerNameText = new TextView(this);
                    playerNameText.setTextSize(26);
                    currentName = gameData.players[i].name;
                    playerPositionAndName = " " + Integer.toString(playerPosition) + ". " + currentName;
                    playerPosition++;
                    playerNameText.setText(playerPositionAndName);
                    //playerNameText.setHeight(150);
                    playerNameText.setBackgroundResource(R.color.white);
                    tr.addView(playerNameText);
                    TextView playerScoreText = new TextView(this);
                    playerScoreText.setTextSize(26);
                    playerScoreText.setText(Integer.toString(gameData.players[i].totalScore));
                    playerScoreText.setGravity(Gravity.CENTER);
                    playerScoreText.setBackgroundResource(R.color.white);
                    //playerScoreText.setHeight(150);
                    tr.addView(playerScoreText);
                    /*txtGeneric.setHeight(30);
                    txtGeneric.setWidth(50);
                    txtGeneric.setTextColor(Color.BLUE);*/
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