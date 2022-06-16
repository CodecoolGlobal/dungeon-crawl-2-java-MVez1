package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class AggressiveMonster extends Actor {


    public AggressiveMonster(Cell cell) {
     super(cell);
     this.setFatality(5);
    }

    @Override
    public void move() {
        if (distanceToHero() == 1) {
            Actor player = this.getCell().getGameMap().getPlayer();
            player.setHealth(player.getHealth()-this.getFatality());
        }

    }

    @Override
    public String getTileName() {
        return "aggressive monster";
    }

    public int distanceToHero() {
        int xPlayer = this.getCell().getGameMap().getPlayer().getX();
        int yPlayer = this.getCell().getGameMap().getPlayer().getY();
        int dx = Math.abs(this.getCell().getX()-xPlayer);
        int dy = Math.abs(this.getCell().getY()-yPlayer);
        return Math.max(dx, dy);
    }
}
