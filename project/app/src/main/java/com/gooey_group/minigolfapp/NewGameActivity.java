package com.gooey_group.minigolfapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewGameActivity extends AppCompatActivity {
//    Game newGame;
    int numPlayers, numHoles;
    String boardName;
    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        numPlayers = 4;
        Button plus = (Button) findViewById(R.id.addbtn);
        Button minus = (Button) findViewById(R.id.minusbtn);
        display = (TextView) findViewById(R.id.playertxt);
        EditText name = (EditText) findViewById(R.id.ScoreboardEditTxt);

        boardName = name.getText().toString();

        display.setText(numPlayers);


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
                display.setText(numPlayers);
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numPlayers == 0){
                    display.setText(numPlayers);
                }//end if
                else{
                    numPlayers = numPlayers - 1;
                    display.setText(numPlayers);
                }
            }
        });


        //BUTTON for Creating the game
    Button createGameBtn = findViewById(R.id.create_game_bttn);
        createGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewGameActivity.this, ScoreboardActivity.class);
                startActivity(intent);
            }
        });

    }//end onCreate




}//end NewGameActivity