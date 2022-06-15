package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Sword;

import java.util.ArrayList;
import java.util.List;

public class Player extends Actor {

    private List<Item> Inventory = new ArrayList<>();

    public Player(Cell cell) {
        super(cell);
        this.setFatality(5);
    }

    public String getTileName() {
        return "player";
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


    public void attackMonster(Actor Monster) {
        Monster.setHealth(Monster.getHealth()-getMaxFatality());
        if (Monster.getHealth() > 0) {
            this.setHealth(this.getHealth()-Monster.getFatality());
        } else {
            this.getCell().setActor(null);
            Monster.getCell().setActor(this);
            this.setCell(Monster.getCell());
        }
    }

    public int getMaxFatality() {
        int maxFatality = this.getFatality();
        if (Inventory != null) {
            for (Item item : Inventory) {
                if (item.getFatality() > maxFatality) {
                    maxFatality = item.getFatality();
                }
            }
        }
        return maxFatality;
    }

}
