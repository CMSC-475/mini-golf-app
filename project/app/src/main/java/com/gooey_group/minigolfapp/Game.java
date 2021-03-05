package com.gooey_group.minigolfapp;

import android.text.InputType;

import java.io.Serializable;
import java.util.Arrays;

public class Game implements Serializable {
    String boardName;
    int numPlayers;
    int numHoles;
    Player[] players;

    //may want to omit for now
    //use System.currentTimeMillis() to set
    long startTime;
    long endTime;
    long elapsedTime;

    public Game(String name, int numPlayers, int numHoles){
        boardName = name;
        this.numHoles = numHoles;
        this.numPlayers = numPlayers;
        players = new Player[numPlayers];

        for(int i = 0; i < players.length; i++) {
            players[i] = new Player(numHoles);

        }

        //todo: implement time later 
    }

    public void setPlayerNames(String[] playerNames){
        //todo: assert that the arrays are same size to ensure no index errors?
        for(int i = 0; i < players.length; i++) {
            players[i].setName(playerNames[i]);
        }
    }
}
