package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.List;

public class Player extends Actor {

    private List<Item> Inventory = new List<Item>();

    public Player(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "player";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < Inventory.size()-1; i++) {
            sb.append(Inventory.get(i).getName() + ", ");
        }
        sb.append(Inventory.get(Inventory.size()-1).getName());
        sb.append("]");
        return sb.toString();

    }

}
