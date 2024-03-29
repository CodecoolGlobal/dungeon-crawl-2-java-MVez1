package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Actor;

public class Sword extends Item {
    public Sword(Cell cell) {
        super(cell);
        this.setFatality(10);
    }

    @Override
    public String getTileName() {
        return "sword";
    }
}
