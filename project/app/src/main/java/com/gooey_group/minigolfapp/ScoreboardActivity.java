package com.gooey_group.minigolfapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import static com.gooey_group.minigolfapp.R.id.score_tbl;

public class ScoreboardActivity extends AppCompatActivity {

    TableLayout scoreboard;
    int numPlayers = 1;
    int numHoles = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        Game dummyGame  = new Game("boardname", 1, 9);

        dummyGame.players[0].points[0] = 1;
        dummyGame.players[0].points[1] = 2;
        dummyGame.players[0].totalScore = 3;
        scoreboard = findViewById(score_tbl);

        setupTable();


        Button endGameBtn = findViewById(R.id.end_game_btn);
        endGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                // endGame();
                Intent intent = new Intent(ScoreboardActivity.this, EndGameActivity.class);
                intent.putExtra("GameInfo", dummyGame);
                startActivity(intent);
            }
        });
    }

    public void setupTable(){

        TableRow nameRow = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT);
        nameRow.setLayoutParams(lp);

        TextView hole_lbl = new TextView(this);
        hole_lbl.setText("Hole");
        hole_lbl.setTypeface(null, Typeface.BOLD);
        hole_lbl.setGravity(Gravity.CENTER);
        nameRow.addView(hole_lbl);

        ViewGroup.LayoutParams headerLayout = hole_lbl.getLayoutParams();
        headerLayout.width = 150;
        hole_lbl.setLayoutParams(headerLayout);

        TextView name_lbl = new TextView(this);
        name_lbl.setText("Player 1");
        name_lbl.setTypeface(null, Typeface.BOLD);
        name_lbl.setGravity(Gravity.CENTER);
        nameRow.addView(name_lbl);
        name_lbl.setLayoutParams(headerLayout);

        scoreboard.addView(nameRow,0);

        ViewGroup.LayoutParams cellLayout = headerLayout;
        cellLayout.height = 100;

        for(int i = 1; i <= numHoles; i++) { //numHoles = num rows

                TableRow tr = new TableRow(this);
                tr.setLayoutParams(lp);
                TextView text = new TextView(this);
                text.setText(Integer.toString(i));
                text.setGravity(Gravity.CENTER);
                tr.addView(text);
                text.setLayoutParams(cellLayout);
                scoreboard.addView(tr, i);

        }

        setupScoreInputs();
    }

    public void setupScoreInputs(){

        for (int i = 1; i<= numHoles; i++){
            View view = scoreboard.getChildAt(i); // a row
            if (view instanceof TableRow) {

                TableRow row = (TableRow) view;

                for(int j = 0; j< numPlayers; j++){

                    EditText scoreInput = new EditText(this);
                    scoreInput.setInputType(InputType.TYPE_CLASS_NUMBER);

                    row.addView(scoreInput);

                    ViewGroup.LayoutParams inputLayout = scoreInput.getLayoutParams();
                    inputLayout.width = 150;
                    scoreInput.setLayoutParams(inputLayout);

                }


            }
        }
    }

    public void endGame(){
        for (int i = 1; i<= numHoles; i++){ //iterates rows
            View view = scoreboard.getChildAt(i);
            if (view instanceof TableRow) {

                TableRow row = (TableRow) view;

                for (int j = 1; j<= numPlayers; j++){ //iterates columns
                    View view2 = row.getChildAt(i);
                    if (view2 instanceof EditText) {

                        EditText scorebox = (EditText) view2;
                        int score = Integer.parseInt(scorebox.getText().toString());
                        //todo: assign values to each Player in Game, will need to use index - 1
                        //todo: also calc total here?
                    }
                }



            }
        }
    }
}