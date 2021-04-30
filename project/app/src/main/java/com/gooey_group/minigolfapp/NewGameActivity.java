package com.gooey_group.minigolfapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;



public class NewGameActivity extends AppCompatActivity {
    int numPlayers, numHoles = 9; //TODO: set this properly later
    TextView display;
    //    EditText playerNames;
    LinearLayout linearLayout;
//    ArrayList<String> names;
//    ArrayList<LinearLayout> linList;

    ArrayList<TextView> textChildren;
    ArrayList<EditText> editChildren;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        linearLayout = findViewById(R.id.playerLayout);
        textChildren = new ArrayList<>();
        editChildren = new ArrayList<>();
//        getSupportActionBar().hide();

//        default to 2 players
        numPlayers = 1;

        Button plus = (Button) findViewById(R.id.addbtn);
        Button minus = (Button) findViewById(R.id.minusbtn);
        display = (TextView) findViewById(R.id.playertxt);


        display.setText(Integer.toString(numPlayers)); //u need to use integer.tostring for numbers


        /*
         * NUMOFHOLES BUTTONS
         */
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

        EditText holeField = findViewById(R.id.hole_field);
        holeField.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0)
                    numHoles = Integer.parseInt(holeField.getText().toString());
            }
        });


        /*
         * +/- BUTTONS: Be able to subtract and add players. Defaults to 4
         */
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numPlayers < 9){

                    numPlayers = numPlayers + 1;
                    display.setText(Integer.toString(numPlayers));
                    playerText();
                }//end if
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numPlayers == 1){
                    display.setText(Integer.toString(numPlayers));
                }//end if
                else{
                    delText();
                    numPlayers = numPlayers - 1;
                    display.setText(Integer.toString(numPlayers));
                }
            }
        });


        //create game object

        playerText();
//        Game dummyGame = new Game(numPlayers, numHoles);


        //BUTTON for Creating the game
        Button createGameBtn = findViewById(R.id.create_game_bttn);
        createGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create game object
                Game dummyGame = new Game(numPlayers, numHoles);

                ArrayList<String> playerNames = new ArrayList<String>(numPlayers);

                for(int i = 0; i < numPlayers ; i++){
                    playerNames.add("Player " + (i+1));
                }


                for(int i = 0; i < numPlayers ; i++){
                    dummyGame.players[i].setName(editChildren.get(i).getText().toString());
                }

                Intent intent = new Intent(NewGameActivity.this, ScoreboardActivity.class);
                intent.putExtra("createdGame", dummyGame);
                startActivity(intent);
            }
        });

    }//end onCreate

    public void delText() {
        TextView deleteTextView = textChildren.get(textChildren.size()-1);
        deleteTextView.setVisibility(View.GONE);
        textChildren.remove(deleteTextView);
        EditText deleteEditText = editChildren.get(editChildren.size()-1);
        deleteEditText.setVisibility(View.GONE);
        editChildren.remove(deleteEditText);

    }//delText

    public void playerText(){

        LinearLayout play = new LinearLayout(this);
        play.setOrientation(LinearLayout.VERTICAL);

        linearLayout.addView(play);

        TextView playerTextView = new TextView(this);
        playerTextView.setText("Player " + String.valueOf(numPlayers));
        playerTextView.setTextSize(24);

        EditText editText = new EditText(this);
        editText.setHint("Player " + String.valueOf(numPlayers) + " Name");

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        playerTextView.setLayoutParams(params);
        play.addView(playerTextView);
        play.addView(editText);
        textChildren.add(playerTextView);
        editChildren.add(editText);


    }//end playertext



}//end NewGameActivity