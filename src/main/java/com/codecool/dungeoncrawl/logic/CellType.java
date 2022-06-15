package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty", false),
    FLOOR("floor", false),
    WALL("wall", true),
    OPEN_DOOR("open door", false),
    CLOSED_DOOR("closed door", true);

    private final String tileName;
    private final boolean barrier;

    CellType(String tileName, boolean barrier) {
        this.tileName = tileName;
        this.barrier = barrier;
    }

    public String getTileName() {
        return tileName;
    }

    public boolean isBarrier() {
        return barrier;
    }
}
