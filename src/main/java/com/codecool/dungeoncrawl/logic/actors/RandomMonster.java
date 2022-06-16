package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.Random;

public class RandomMonster extends Actor {


    public RandomMonster(Cell cell) {
        super(cell);
        this.setFatality(2);
    }

    public String getTileName() {
        return "random monster";
    }

    public void move() {
        boolean validMove = false;
        while (!validMove) {
            Cell neighbor = getRandomNeighbor();
            int dx = this.getCell().getX() - neighbor.getX();
            int dy = this.getCell().getY() - neighbor.getY();
            if (validMove(dx, dy)) {
                super.move(dx, dy);
                validMove = true;
            }
        }

    }

    public Cell getRandomNeighbor() {
        Random random = new Random();
        switch(random.nextInt(4)) {
            case(1):
                return this.getCell().getNeighbor(0, 1);
            case(2):
                return this.getCell().getNeighbor(-1, 0);
            case(3):
                return this.getCell().getNeighbor(1, 0);
            default:
                return this.getCell().getNeighbor(0, -1);
        }
    }
}
