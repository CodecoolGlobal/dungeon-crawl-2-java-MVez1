package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Sword;

import java.util.List;

public class Player extends Actor {

    private List<Item> Inventory;

    public Player(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "player";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        if (Inventory != null) {
            for (int i = 0; i < Inventory.size() - 1; i++) {
                sb.append(Inventory.get(i).getTileName() + ", ");
            }
            sb.append(Inventory.get(Inventory.size() - 1).getTileName());
        }
        sb.append("]");
        return sb.toString();

    }


    public void attackMonster(Actor Monster) {
        boolean hasSword = false;
        if (Inventory != null) {
          for (Item item : Inventory) {
              if (item instanceof Sword) {
                  hasSword = true;
                  break;
              }
          }
        }
        if (hasSword) {Monster.setHealth(Monster.getHealth()-10);}
        else {Monster.setHealth(Monster.getHealth()-5);}
        if (Monster.getHealth() > 0) {
            this.setHealth(this.getHealth()-2);
        } else {
            this.getCell().setActor(null);
            Monster.getCell().setActor(this);
            this.setCell(Monster.getCell());
        }
    }

}
