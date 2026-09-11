package com.github.citybuilder.model.map;

import java.util.ArrayList;
import java.util.List;

import com.github.citybuilder.rules.ConstructionRules;
import com.github.citybuilder.utils.math.*;

public class WorldMap {

    private final long width;
    private final long height;

    private final List<List<Tile>> mapGrid;

    public WorldMap(long width, long height) {
        this.width = width;
        this.height = height;

        double scale = 0.03;

        final NoiseGenerator elevationNoise = new NoiseGenerator();

        this.mapGrid = new ArrayList<>();

        for (int i = 0; i < width; i++) {
            final List<Tile> row = new ArrayList<>();
            for (int j = 0; j < height; j++) {

                double elevation = elevationNoise.getNoise(i, j, scale, 4);
                double riverVal = Math.abs(elevationNoise.getNoise(i, j, 0.04, -2) - 0.5);
                
                row.add(new Tile(i, j, TileType.getTileTypeFromFloat(elevation, riverVal)));
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
     * gets the Tile in that position
     * @param position .
     * @return the Tile
     */
    public TileType getTileType(int x, int y) {
        return this.mapGrid.get(x).get(y).getType();
    }

    /**
     * changes a tile type
     * @param position .
     * @param tileType .
     */
    public void setTileType(int x, int y, TileType tileType) {

        if(ConstructionRules.canBuild(this.getTileType(x, y), tileType)) {
            final var tileToSet = new Tile(x, y, tileType);
            this.mapGrid.get(x).set(y, tileToSet);
        }
    }

    public long getWidth() {
        return this.width;
    }

    public long getHeight() {
        return this.height;
    }
    
    public long getNumberOfTileTypes(TileType tile) {

        long count = 0;

        for(var list: this.mapGrid) {
            for(var t: list) {
                if(t.getType().equals(tile)) {
                    count++;
                }
            }
        }
        
        return count;
    }
}
