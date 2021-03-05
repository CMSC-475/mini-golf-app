package com.gooey_group.minigolfapp;

import java.io.Serializable;

public class Player implements Comparable<Player>, Serializable {
    int[] points; //hole 1 = points[0]
    int numHoles;
    int totalScore;
    String name;

    public Player(int numHoles){
        this.numHoles = numHoles;
        totalScore = 0;
        points = new int[numHoles];
        name = "Player";
    }

    public void setName(String newName){
        name = newName;
    }

    @Override
    public int compareTo(Player other) {
        // usually toString should not be used,
        // instead one of the attributes or more in a comparator chain
        if (this.totalScore > other.totalScore) { return 1; }
        else if (this.totalScore == other.totalScore) { return 0; }
        else { return -1; }
    }
}
