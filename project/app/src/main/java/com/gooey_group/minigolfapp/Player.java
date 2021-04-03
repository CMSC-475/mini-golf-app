package com.gooey_group.minigolfapp;

import java.io.Serializable;
import java.util.Arrays;

public class Player implements Comparable<Player>, Serializable {
    int[] points; //hole 1 = points[0]
    int numHoles;
    int totalScore;
    String name;

    public Player(int numHoles){
        this.numHoles = numHoles;
        totalScore = 0;
        points = new int[numHoles];
        Arrays.fill(points, 0);
        name = "Player";
    }

    public void setName(String newName){
        name = newName;
    }

    @Override
    public int compareTo(Player other) {
        if (this.totalScore > other.totalScore) { return 1; }
        else if (this.totalScore == other.totalScore) { return 0; }
        else { return -1; }
    }
}
