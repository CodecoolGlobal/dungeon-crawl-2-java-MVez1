package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class randomMonster extends Actor {


    public randomMonster(Cell cell) {
        super(cell);
        this.setFatality(2);
    }

    public String getTileName() {
        return "randomMonster";
    }

    public void move() {

    }

}
