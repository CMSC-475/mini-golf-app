package com.gooey_group.minigolfapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

public class EndGameActivity extends AppCompatActivity {

    int numberOfPlayers;
    Button homepageButton;
    String playerWinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        homepageButton = findViewById(R.id.returnHP);
        homepageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EndGameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}