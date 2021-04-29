package com.gooey_group.minigolfapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import static com.gooey_group.minigolfapp.R.id.header_tbl;
import static com.gooey_group.minigolfapp.R.id.score_tbl;

public class ScoreboardActivity extends AppCompatActivity {

    TableLayout scoreboard;
    TableLayout headerRow;
    //defaults
    int numPlayers = 2;
    int numHoles = 9;
    Game currentGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);


        currentGame = (Game) getIntent().getSerializableExtra("createdGame");
        headerRow = findViewById(header_tbl);
        scoreboard = findViewById(score_tbl);
        numPlayers = currentGame.numPlayers;
        numHoles = currentGame.numHoles;

        setupTable();


        Button endGameBtn = findViewById(R.id.end_game_btn);
        endGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endGame();
                Intent intent = new Intent(ScoreboardActivity.this, EndGameActivity.class);
                intent.putExtra("GameInfo", currentGame);
                startActivity(intent);
            }
        });
    }

    public void setupTable(){

        TableRow nameRow = new TableRow(this);
        nameRow.setBackgroundColor(Color.parseColor("#b3e8b5"));
        TableRow.LayoutParams lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT);
        nameRow.setLayoutParams(lp);

        TextView hole_lbl = new TextView(this);
        hole_lbl.setText("Hole");
        hole_lbl.setTextSize(25);
        hole_lbl.setTypeface(null, Typeface.BOLD);
        hole_lbl.setGravity(Gravity.CENTER);
        //hole_lbl.setBackgroundColor(Color.parseColor("#d8d2cd"));
        nameRow.addView(hole_lbl);

        ViewGroup.LayoutParams headerLayout = hole_lbl.getLayoutParams();
        headerLayout.width = 230; //width of cell
        hole_lbl.setLayoutParams(headerLayout);

        for(int i = 0; i < numPlayers; i++ ){
            TextView name_lbl = new TextView(this);
            name_lbl.setText(currentGame.players[i].name);
            name_lbl.setTypeface(null, Typeface.BOLD);
            name_lbl.setGravity(Gravity.CENTER);
            //name_lbl.setTextSize(25);
            nameRow.addView(name_lbl);
            name_lbl.setLayoutParams(headerLayout);
        }

        headerRow.addView(nameRow,0);

        ViewGroup.LayoutParams cellLayout = headerLayout;
        cellLayout.height = 200;

        //add hole number labels
        for(int i = 0; i < numHoles; i++) { //numHoles = num rows

                TableRow tr = new TableRow(this);
                tr.setLayoutParams(lp);
                TextView text = new TextView(this);
                text.setText(Integer.toString(i+1));
                //text.setBackgroundColor(Color.parseColor("#d8d2cd"));
                text.setTextSize(25);
                text.setGravity(Gravity.CENTER);
                tr.addView(text);
                text.setLayoutParams(cellLayout);
                scoreboard.addView(tr, i);

        }

        setupScoreInputs();
        colorRows();
    }

    public void setupScoreInputs(){

        for (int i = 0; i< numHoles; i++){
            View view = scoreboard.getChildAt(i); // a row
            if (view instanceof TableRow) {

                TableRow row = (TableRow) view;

                for(int j = 0; j< numPlayers; j++){

                    EditText scoreInput = new EditText(this);
                    scoreInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                    scoreInput.setTextSize(25);
                    scoreInput.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    scoreInput.setTextColor(Color.parseColor("#000000"));
                    row.addView(scoreInput);

                    ViewGroup.LayoutParams inputLayout = scoreInput.getLayoutParams();
                    inputLayout.width = 230;
                    scoreInput.setLayoutParams(inputLayout);

                }
            }
        }
    }

    public void colorRows(){
        for (int i = 0; i< numHoles; i++){
            View view = scoreboard.getChildAt(i); // a row
            if (view instanceof TableRow) {

                TableRow row = (TableRow) view;
                if(i%2!=0)
                    row.setBackgroundColor(Color.parseColor("#f2eeeb"));

            }
        }
    }

    public void endGame(){
        int score = 0;
        for (int i = 0; i < numHoles; i++){ //iterates rows
            View view = scoreboard.getChildAt(i);
            if (view instanceof TableRow) {

                TableRow row = (TableRow) view;

                for (int j = 1; j<= numPlayers; j++){ //iterates columns
                    View view2 = row.getChildAt(j);
                    if (view2 instanceof EditText) {

                        EditText scorebox = (EditText) view2;

                        if(scorebox.getText().toString().isEmpty()){
                            score = 0;
                        }else {
                            score = Integer.parseInt(scorebox.getText().toString());
                        }

                        //fill game obj with points
                        currentGame.players[j-1].points[i] = score;
                        currentGame.players[j-1].totalScore += score;
                    }
                }
            }
        }
    }
}