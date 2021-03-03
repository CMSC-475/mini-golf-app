package com.gooey_group.minigolfapp;

public class Player {
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
}
