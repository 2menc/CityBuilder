package com.github.citybuilder.model.map;

public class Tile {

    private final int x;
    private final int y;
    private final TileType type;

    /** for ecs */
    private int id;

    public Tile(int x, int y, TileType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public TileType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
