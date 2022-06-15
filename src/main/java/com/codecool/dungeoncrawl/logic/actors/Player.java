package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Player extends Actor {

    private List<Item> Inventory = new ArrayList<>();

    public Player(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "player";
    }

    public void addToInventory(Item item) {
        Inventory.add(item);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        if (Inventory.size() > 0) {
            for (int i = 0; i < Inventory.size() - 1; i++) {
                sb.append(Inventory.get(i).getTileName() + ", ");
            }
            sb.append(Inventory.get(Inventory.size() - 1).getTileName());
        }
        sb.append("]");
        return sb.toString();

    }

}
