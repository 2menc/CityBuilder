package com.github.Citybuilder.map;

import java.util.ArrayList;
import java.util.List;

public class WorldMap {

    private final int width;
    private final int height;

    private final List<List<Tile>> mapGrid;

    public WorldMap(int width, int height) {
        this.width = width;
        this.height = height;

        this.mapGrid = new ArrayList<>();

        for (int i = 0; i < width; i++) {
            final List<Tile> row = new ArrayList<>();
            for (int j = 0; j < height; j++) {
                row.add(new Tile(i, j, TileType.GRASS));
            }
            this.mapGrid.add(row);
        }

    }

    /**
     * checks if the desired position is inside the map boundaries
     * @param position .
     * @return true if it is
     */
    public boolean isValid(int x, int y) {
        return x >= 0 && x < width &&
                y >= 0 && y < height;
    }

    /**
     * gets the Tile in that position
     * @param position .
     * @return the Tile
     */
    public Tile getTile(int x, int y) {
        return this.mapGrid.get(x).get(y);
    }

    /**
     * changes a tile type
     * @param position .
     * @param tileType .
     */
    public void setTileType(int x, int y, TileType tileType) {
        final var tileToSet = new Tile(x, y, tileType);
        this.mapGrid.get(x).set(y, tileToSet);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void printToConsole() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                
                if (getTile(i, j).getType() == TileType.GRASS) {
                    System.out.print("#");
                } else if (getTile(i, j).getType() == TileType.ROAD) {
                    System.out.print(0);
                }
            }
        System.out.println("\n");
       }
    }
}
