package com.gooey_group.minigolfapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewGameActivity extends AppCompatActivity {
    int numPlayers, numHoles;
    String boardName;
    TextView display;
    EditText playerNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        numPlayers = 2;
        Button plus = (Button) findViewById(R.id.addbtn);
        Button minus = (Button) findViewById(R.id.minusbtn);
        display = (TextView) findViewById(R.id.playertxt);
        EditText name = (EditText) findViewById(R.id.ScoreboardEditTxt);

        boardName = name.getText().toString();

        display.setText(Integer.toString(numPlayers)); //u need to use integer.tostring for numbers


        /*
         * NUMOFHOLES BUTTONS
         */
        //todo: custom button
        Button nineBtn = findViewById(R.id.nineHoleBtn);
        nineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numHoles = 9;
            }
        });

        Button eighteenBtn = findViewById(R.id.eighteenHoleBtn);
        eighteenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numHoles = 18;
            }
        });


        /*
         * +/- BUTTONS: Be able to subtract and add players. Defaults to 4
         */
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numPlayers = numPlayers + 1;
                display.setText(Integer.toString(numPlayers));
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numPlayers == 1){
                    display.setText(Integer.toString(numPlayers));
                }//end if
                else{
                    numPlayers = numPlayers - 1;
                    display.setText(Integer.toString(numPlayers));
                }
            }
        });

        //create game object
        Game dummyGame = new Game(boardName, numPlayers, numHoles);

        EditText player1 = (EditText) findViewById(R.id.player1);
        String player1Name = player1.getText().toString();

        EditText player2 = (EditText) findViewById(R.id.player2);
        String player2Name = player1.getText().toString();

        dummyGame.players[0].setName(player1Name);
        dummyGame.players[1].setName(player2Name);

        //BUTTON for Creating the game
    Button createGameBtn = findViewById(R.id.create_game_bttn);
        createGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewGameActivity.this, ScoreboardActivity.class);
                intent.putExtra("createdGame", dummyGame);
                startActivity(intent);
            }
        });

    }//end onCreate




}//end NewGameActivity