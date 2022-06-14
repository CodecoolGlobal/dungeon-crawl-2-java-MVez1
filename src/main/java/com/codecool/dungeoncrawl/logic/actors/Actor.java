package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        Actor Monster = nextCell.getActor();
        if (validMove(dx, dy)) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        } else if (cell.getActor() instanceof Player && Monster instanceof Skeleton) {
            ((Player) cell.getActor()).attackMonster(Monster);
        }
    }

    public boolean validMove(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (!nextCell.getType().getTileName().equals("wall") && nextCell.getActor() == null) {
            return true;
        }
        return false;
    }


    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }


}
