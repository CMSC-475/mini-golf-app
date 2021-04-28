package com.gooey_group.minigolfapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.graphics.Typeface;
import java.util.Arrays;
import android.content.Context;

import java.io.Serializable;

public class EndGameActivity extends AppCompatActivity implements Serializable {
    final int MAX_SCORE = 999;
    final int TEXT_SIZE = 26;
    final int MAX_NAME_LENGTH = 10;
    final int SP_PADDING_SIZE = 4;

    Button homepageButton;
    int tableSize;
    Game gameData;
    int totalPlayers;
    int currentPlayerScore = 0;
    String currentPlayerName = "";
    Player currentPlayerInfo;
    TextView currentPlayerNameView;
    TextView currentPlayerScoreView;


    // TODO: improved dynamic table, table shadow, test on other emulators
    // make headers consistent, main page button not centered, make buttons same sizes
    // player cap?
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

            // check if any names are empty
            for (int j = 0; j < totalPlayers; j++) {
                if (gameData.players[j].name.isEmpty()) {
                    gameData.players[j].name = "Player " + Integer.toString(j+1);
                }
            }
            Arrays.sort(gameData.players);
            TableLayout playersTable = (TableLayout) findViewById(R.id.table1);
            ScrollView scrollTable = (ScrollView) findViewById(R.id.scrollTable);
            if (totalPlayers <= 0) {
                playersTable.setVisibility(View.GONE);
                scrollTable.setVisibility(View.GONE);
                currentPlayerNameView = (TextView) findViewById(R.id.name1);
                currentPlayerScoreView = (TextView) findViewById(R.id.score1);
                currentPlayerNameView.setText("No One!");
                currentPlayerScoreView.setText("0");
            }
            // check for certain cases so the app screen is formatted better
            else if (totalPlayers == 1) {
                playersTable.setVisibility(View.GONE);
                scrollTable.setVisibility(View.GONE);

                currentPlayerInfo = gameData.players[0];
                currentPlayerNameView = (TextView) findViewById(R.id.name1);
                currentPlayerScoreView = (TextView) findViewById(R.id.score1);
                currentPlayerName = currentPlayerInfo.name;
                currentPlayerName = checkValidName(currentPlayerName);
                currentPlayerNameView.setText(currentPlayerName);

                currentPlayerScore = currentPlayerInfo.totalScore;
                currentPlayerScore = checkValidScore(currentPlayerScore);

                currentPlayerScoreView.setText("Score: " + Integer.toString(currentPlayerScore));
            }
            else {
                Typeface typeface = ResourcesCompat.getFont(this, R.font.open_sans);
                int paddingSize = spToPx(SP_PADDING_SIZE, this);
                currentPlayerInfo = gameData.players[0];
                currentPlayerNameView = (TextView) findViewById(R.id.name1);
                currentPlayerName = currentPlayerInfo.name;
                currentPlayerName = checkValidName(currentPlayerName);

                currentPlayerScoreView = (TextView) findViewById(R.id.score1);
                currentPlayerNameView.setText(currentPlayerName);
                currentPlayerScore = currentPlayerInfo.totalScore;
                currentPlayerScore = checkValidScore(currentPlayerScore);

                currentPlayerScoreView.setText("Score: " + Integer.toString(currentPlayerScore));
                int playerPosition = 2;
                String playerPositionAndName = "";
                TableRow trOne = (TableRow) findViewById((R.id.row1));
                currentPlayerInfo = gameData.players[1];
                currentPlayerName = currentPlayerInfo.name;
                currentPlayerName = checkValidName(currentPlayerName);

                currentPlayerScore = currentPlayerInfo.totalScore;
                currentPlayerScore = checkValidScore(currentPlayerScore);

                // Tie Condition
                if (currentPlayerScore == gameData.players[0].totalScore) {
                    TextView changeWinnerText = (TextView) findViewById(R.id.winnerText);
                    changeWinnerText.setText("It's a tie!");
                    playerPositionAndName = " " + Integer.toString(1) + ". " + currentPlayerName;
                }
                else {
                    playerPositionAndName = " " + Integer.toString(playerPosition) + ". " + currentPlayerName;
                    playerPosition++;
                }
                TextView playerTwoNameView = (TextView) findViewById(R.id.row1Name);
                playerTwoNameView.setPadding(paddingSize,paddingSize,paddingSize,paddingSize);
                playerTwoNameView.setText((playerPositionAndName));
                playerTwoNameView.setTextSize(TEXT_SIZE);
                TextView playerTwoScoreView = (TextView) findViewById(R.id.row1Score);
                playerTwoScoreView.setPadding(paddingSize,paddingSize,paddingSize,paddingSize);

                playerTwoScoreView.setText(Integer.toString(currentPlayerScore));
                playerTwoScoreView.setTextSize(TEXT_SIZE);
                for(int i = 2; i < tableSize; i++) {
                    TableRow tr =  new TableRow(this);
                    //tr.setPadding(paddingSize, paddingSize, paddingSize, paddingSize);
                    TextView playerNameText = new TextView(this);

                    playerNameText.setTextSize(TEXT_SIZE);
                    currentPlayerInfo = gameData.players[i];
                    currentPlayerName = currentPlayerInfo.name;
                    currentPlayerName = checkValidName(currentPlayerName);

                    playerPositionAndName = " " + Integer.toString(playerPosition) + ". " + currentPlayerName;
                    playerPosition++;
                    playerNameText.setText(playerPositionAndName);
                    //playerNameText.setHeight(150);
                    //playerNameText.setBackgroundResource(R.color.white);
                    playerNameText.setBackgroundResource(R.drawable.table_outline);
                    playerNameText.setTypeface(typeface);
                    playerNameText.setPadding(paddingSize, paddingSize, paddingSize, paddingSize);

                    tr.addView(playerNameText);
                    TextView playerScoreText = new TextView(this);

                    playerScoreText.setTextSize(TEXT_SIZE);
                    playerScoreText.setTypeface(typeface);

                    currentPlayerScore = currentPlayerInfo.totalScore;
                    currentPlayerScore = checkValidScore(currentPlayerScore);
                    playerScoreText.setText(Integer.toString(currentPlayerScore));

                    playerScoreText.setGravity(Gravity.CENTER);
                    //playerScoreText.setBackgroundResource(R.color.white);
                    playerScoreText.setBackgroundResource(R.drawable.table_outline);
                    playerScoreText.setPadding(paddingSize, paddingSize, paddingSize, paddingSize);

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
        playerOne.setName("Ren");
        Player playerTwo = new Player(9);
        playerTwo.totalScore = 6;
        playerTwo.setName("ASOIDASOIDASIOd");
        Player playerThree = new Player(9);
        playerThree.totalScore = 7;
        playerThree.setName("");
        Player playerFour = new Player(9);
        playerFour.totalScore = -1;
        playerFour.setName("");
        Player playerFive = new Player(9);
        playerFive.totalScore = 9;
        playerFive.setName("");
        Player playerSix = new Player(9);
        playerSix.totalScore = 12023;
        playerSix.setName("");
        gameData = new Game(6, 9);
        gameData.players[0] = playerOne;
        gameData.players[1] = playerTwo;
        gameData.players[2] = playerThree;
        gameData.players[3] = playerFour;
        gameData.players[4] = playerFive;
        gameData.players[5] = playerSix;
    }

    public static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    public String checkValidName(String currentName) {
        if (currentName.length() > MAX_NAME_LENGTH) {
            currentName = currentName.substring(0, MAX_NAME_LENGTH) + ".";
        }
        return currentName;
    }

    public int checkValidScore(int currentScore) {
        if (currentScore < 0) {
            currentScore = 0;
        }
        if (currentScore > MAX_SCORE) {
            currentScore = MAX_SCORE;
        }
        return currentScore;
    }
}