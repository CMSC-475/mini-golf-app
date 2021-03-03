package com.gooey_group.minigolfapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import static com.gooey_group.minigolfapp.R.id.score_tbl;

public class ScoreboardActivity extends AppCompatActivity {

    TableLayout scoreboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        scoreboard = findViewById(score_tbl);

        setupTable();

    }

    public void setupTable(){

        int numHoles = 9;

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
        name_lbl.setText("Name");
        name_lbl.setTypeface(null, Typeface.BOLD);
        name_lbl.setGravity(Gravity.CENTER);
        nameRow.addView(name_lbl);
        name_lbl.setLayoutParams(headerLayout);

        scoreboard.addView(nameRow,0);

        ViewGroup.LayoutParams cellLayout = headerLayout;
        cellLayout.height = 100;

        for(int i = 1; i <= numHoles; i++) {

                TableRow tr = new TableRow(this);
                tr.setLayoutParams(lp);
                TextView text = new TextView(this);
                text.setText(Integer.toString(i));
                text.setGravity(Gravity.CENTER);
                tr.addView(text);
                text.setLayoutParams(cellLayout);
                scoreboard.addView(tr, i);

        }


    }

    public void setupTableRow(){
        /* int numPlayers = 2;
        for (int i = 0; i<numPlayers; i++){
            View view = scoreboard.getChildAt(i);
            if (view instanceof TableRow) {
                TableRow row = (TableRow) view;
                if( something you want to check ) {
                    table.removeViewAt(i);
                    // or...
                    table.removeView(row);
                }


            }
        }

         */


        /*
        EditText scoreInput = new EditText(this);
                scoreInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                scoreInput.setLayoutParams(cellLayout);
         */
    }
}