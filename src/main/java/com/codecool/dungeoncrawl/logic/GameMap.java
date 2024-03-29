package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.AggressiveMonster;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.RandomMonster;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    private Player player;

    private RandomMonster randomMonster;

    private AggressiveMonster aggressiveMonster;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public RandomMonster getRandomMonster() {
        return randomMonster;
    }

    public void setRandomMonster(RandomMonster randomMonster) {
        this.randomMonster = randomMonster;
    }

    public AggressiveMonster getAggressiveMonster() {
        return this.aggressiveMonster;
    }

    public void setAggressiveMonster(AggressiveMonster aggressiveMonster) {
        this.aggressiveMonster = aggressiveMonster;
    }
}
