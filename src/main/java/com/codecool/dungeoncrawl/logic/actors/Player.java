package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;
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

    @Override
    public void move(int dx, int dy) {
        if (!checkNeighbor(dx, dy)) {
            Cell nextCell = getCell().getNeighbor(dx, dy);
            if (validMove(dx, dy)) {
                getCell().setActor(null);
                nextCell.setActor(this);
                setCell(nextCell);
            }
        }
    }

    private boolean checkNeighbor(int dx, int dy) {
        Actor monster = getCell().getNeighbor(dx, dy).getActor();
        if (monster instanceof Skeleton || monster instanceof RandomMonster || monster instanceof AggressiveMonster) {
            ((Player) getCell().getActor()).attackMonster(monster);
            return true;
        }
        if (getCell().getNeighbor(dx, dy).getType().isBarrier()) {
            if (getCell().getNeighbor(dx, dy).getType().equals(CellType.CLOSED_DOOR)) {
                Cell lock = getCell().getNeighbor(dx, dy);
                return !unlock(lock);
            }
            return true;
        }
        return false;
    }

    private boolean unlock(Cell lockedDoor) {
        for (Item item : Inventory) {
            if (item instanceof Key) {
                lockedDoor.setType(CellType.OPEN_DOOR);
                return true;
            }
        }
        return false;
    }


    public void attackMonster(Actor Monster) {
        Monster.setHealth(Monster.getHealth() - getMaxFatality());
        if (Monster.getHealth() > 0) {
            this.setHealth(this.getHealth() - Monster.getFatality());
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

    public void move() {

    }


}
