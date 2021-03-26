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
    Game gameData;
    int totalPlayers;
    Player currentPlayer;
    TextView currentPlayerName;
    TextView currentPlayerScore;

    // TODO: tie condition, dynamic table, table shadow, scrollable table

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        // intent that receives an intent of type Game
        if (getIntent().getSerializableExtra("GameInfo") != null) {
            gameData = (Game) getIntent().getSerializableExtra("GameInfo");
            tableSize = gameData.numPlayers - 1;
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
            else if (totalPlayers == 2) {
                // todo: hard coded table for now, will make dynamic later
                // can possibly just put this in a for loop, with i being used to find gameData.players[i], name(i+1), and score(i+1)
                currentPlayer = gameData.players[0];
                currentPlayerName = (TextView) findViewById(R.id.name1);
                currentPlayerScore = (TextView) findViewById(R.id.score1);
                currentPlayerName.setText(currentPlayer.name);
                currentPlayerScore.setText("Score: " + Integer.toString(currentPlayer.totalScore));

                currentPlayer = gameData.players[1];
                currentPlayerName = (TextView) findViewById(R.id.name2);
                currentPlayerScore = (TextView) findViewById(R.id.score2);
                currentPlayerName.setText("2. " + currentPlayer.name);
                currentPlayerScore.setText(Integer.toString(currentPlayer.totalScore));
            }
            // currently only supports up to four players
            else {
                // COULD ALSO INFLATE
                TableLayout playersTable = (TableLayout) findViewById(R.id.table1);
                for(int i = 1; i < tableSize; i++) {
                    TableRow tr =  new TableRow(this);
                    int numColumns = 2;
                    for(int j = 0; j < numColumns; j++) {
                        TextView txtGeneric = new TextView(this);
                        txtGeneric.setTextSize(18);
                        txtGeneric.setText("Test");
                        tr.addView(txtGeneric);
                        /*txtGeneric.setHeight(30);
                        txtGeneric.setWidth(50);
                        txtGeneric.setTextColor(Color.BLUE);*/
                    }
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
}