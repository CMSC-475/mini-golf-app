package com.gooey_group.minigolfapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.util.Log;
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
import android.widget.Toast;

import org.w3c.dom.Text;

import static com.gooey_group.minigolfapp.R.id.header_tbl;
import static com.gooey_group.minigolfapp.R.id.score_tbl;

public class ScoreboardActivity extends AppCompatActivity {

    TableLayout scoreboard;
    TableLayout headerRow;
    //defaults
    int numPlayers = 2;
    int numHoles = 9;
    int numTables = 1;
    int currentPageGlobal = 1;
    Game currentGame;
    TableRow[] headerRows;
    TableRow[] scoreRows;
    TableRow[][] scoreboards;
    TableRow[] scoreboardPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        currentGame = (Game) getIntent().getSerializableExtra("createdGame");
        headerRow = findViewById(header_tbl);
        scoreboard = findViewById(score_tbl);
        numPlayers = currentGame.numPlayers;
        numHoles = currentGame.numHoles;

        if(numPlayers <=3)
            setupTable();
        else
            setupTableWithPages();


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

        Context context = getApplicationContext();
        CharSequence text = "Swipe on the header to switch between pages!";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }


    public void setupTable(){

        TableRow nameRow = new TableRow(this);
        nameRow.setBackgroundColor(Color.parseColor("#b3e8b5"));
        TableRow.LayoutParams lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT); //set layout of TableRow
        nameRow.setLayoutParams(lp);

        TextView hole_lbl = new TextView(this);
        hole_lbl.setText("Hole");
        hole_lbl.setTextSize(25);
        hole_lbl.setTypeface(null, Typeface.BOLD);
        hole_lbl.setGravity(Gravity.CENTER);
        nameRow.addView(hole_lbl);

        ViewGroup.LayoutParams headerLayout = hole_lbl.getLayoutParams();
        headerLayout.width = 230; //width of cell
        hole_lbl.setLayoutParams(headerLayout);

        //set name headers
        for(int i = 0; i < numPlayers; i++ ){
            TextView name_lbl = new TextView(this);
            name_lbl.setText(currentGame.players[i].name);
            name_lbl.setTypeface(null, Typeface.BOLD);
            name_lbl.setGravity(Gravity.CENTER);
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
                text.setTextSize(25);
                text.setGravity(Gravity.CENTER);
                tr.addView(text);
                text.setLayoutParams(cellLayout);
                scoreboard.addView(tr, i);

        }

        setupScoreInputs();
        colorRows();
    }

    //@SuppressLint("ClickableViewAccessibility")
    public void setupTableWithPages() {

        double temp = ((double)numPlayers / 3) ;
        numTables = ((int) Math.ceil(temp)); //how many pages we need

        TableRow[] headerRows = new TableRow[numTables];

        Log.i("ScoreboardActivity", Integer.toString(numTables));

        ViewGroup.LayoutParams cellLayout = null; //layout for a cell in header
        TableRow.LayoutParams lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT); //layout for entire header

        for(int i = 0; i < numTables; i++) {

            TableRow nameRow = new TableRow(this); //nameRow has Hole header, Player names
            nameRow.setBackgroundColor(Color.parseColor("#b3e8b5"));
            nameRow.setLayoutParams(lp);

            //create hole header
            TextView hole_lbl = new TextView(this);
            hole_lbl.setText("Hole");
            hole_lbl.setTextSize(25);
            hole_lbl.setTypeface(null, Typeface.BOLD);
            hole_lbl.setGravity(Gravity.CENTER);

            nameRow.addView(hole_lbl); //add to nameRow

            cellLayout = hole_lbl.getLayoutParams();

            cellLayout.width = 230; //width of cell
            cellLayout.height = 200; //height of cell 

            hole_lbl.setLayoutParams(cellLayout);

            int jMin = 0;
            int jMax = 1;

            if(i == 0){
                jMin = 0;
                jMax = 3;
            }else if(i == 1) { //pg 2 range
                jMin = 3;
                jMax = 6;
            }else if(i == 2){ //pg 3 range
                jMin = 6;
                jMax = 9;
            }

            //build each name header cell for this page
            for (int j = jMin; j < jMax; j++) {

                TextView name_lbl = new TextView(this);
                if(j < numPlayers) {
                    name_lbl.setText(currentGame.players[j].name);
                }
                name_lbl.setTypeface(null, Typeface.BOLD);
                name_lbl.setGravity(Gravity.CENTER);
                nameRow.addView(name_lbl);
                name_lbl.setLayoutParams(cellLayout);

            }

            headerRows[i] = nameRow;

        }

        headerRow.addView(headerRows[0]); //sets to first page by default
        currentPageGlobal = 1;

        scoreRows = new TableRow[numHoles]; //array of ALL player scores, + the hole number label at index 0

        //add hole number labels
        //iterate all rows of 2nd table and add label to first column
        for(int y = 0; y < numHoles; y++) { //numHoles = num rows

            TableRow tr = new TableRow(this);
            tr.setLayoutParams(lp);
            TextView text = new TextView(this);
            text.setText(Integer.toString(y+1));
            text.setTextSize(25);
            text.setGravity(Gravity.CENTER);
            tr.addView(text);
            text.setLayoutParams(cellLayout);
            scoreRows[y] = tr;
            //scoreboard.addView(tr, y);

        }

        setupScoreInputsWithPages();
        colorRows();


        headerRow.setOnTouchListener(new OnSwipeTouchListener(ScoreboardActivity.this) {

            public void onSwipeRight() {
                if (currentPageGlobal != 1) { //can still go back
                    currentPageGlobal -= 1;
                    Toast.makeText(ScoreboardActivity.this, "page" + currentPageGlobal, Toast.LENGTH_SHORT).show();
                    headerRow.removeAllViews();
                    headerRow.addView(headerRows[currentPageGlobal - 1]);
                    setScoreboardPage();
                }

            }

            public void onSwipeLeft() {
                if(currentPageGlobal < numTables) {//can still go forward
                    currentPageGlobal += 1;
                    Toast.makeText(ScoreboardActivity.this, "page" + currentPageGlobal, Toast.LENGTH_SHORT).show();
                    headerRow.removeAllViews();
                    headerRow.addView(headerRows[currentPageGlobal - 1]);
                    setScoreboardPage();
                }
            }

        });

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

    public void setupScoreInputsWithPages(){

        //add textboxes for each tablerow
        for (int i = 0; i < numHoles; i++){

            TableRow row = scoreRows[i];

            for(int j = 0; j < numPlayers; j++){

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

        scoreboards = new TableRow[numTables][numHoles]; //an array representing all the scoreboard pages


        //separate big array into groups of 4 columns (hole + 3 player cols)
        //each of these groups is a page
        for(int i = 0; i < numTables; i++){ //iterates scoreboard pages

            scoreboardPage = new TableRow[numHoles]; //array of rows (forms pg)
            //TableRow tempRow;

            for(int j=0; j< numHoles; j++) {//iterate rows

                TableRow tempRow;
                tempRow = scoreRows[j];

                if (i == 0) {//pg1
                    tempRow.removeViews(4, tempRow.getChildCount() - 4); //remove last rows
                } else if (i == 1) {//pg 2
                    tempRow.removeViews(1, 3 ); //remove first 3 rows (except hole row)

                    if(tempRow.getChildCount() > 4) {
                        tempRow.removeViews(4, tempRow.getChildCount() - 4); //trim any last rows
                    }

                } else if (i == 2) {//pg3
                    tempRow.removeViews(1, 8); //remove first 7 rows

                }

                scoreboardPage[j] = tempRow; //add trimmed row to scoreboard page

            }

            scoreboards[i] = scoreboardPage; //add page to page arr

        }

        setScoreboardPage();

    }


    public void setScoreboardPage(){
        scoreboard.removeAllViews();

        for(int i = 0; i < numHoles; i++){//set row by row
            //iterate each row in the current scoreboard page
            scoreboard.addView(scoreboards[currentPageGlobal-1][i]);
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