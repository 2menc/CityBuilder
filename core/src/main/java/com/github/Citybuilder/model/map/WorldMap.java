package com.github.citybuilder.model.map;

import com.github.citybuilder.rules.ConstructionRules;
import com.github.citybuilder.utils.math.*;

/**
 * A class that models the game world map.
 */
public class WorldMap {

    private final long width;
    private final long height;

    private final byte[][] mapGrid;

    public WorldMap(long width, long height) {
        this.width = width;
        this.height = height;

        double scale = 0.03;


        final NoiseGenerator elevationNoise = new NoiseGenerator();

        this.mapGrid = new byte[(int) width][(int) height];        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                double elevation = elevationNoise.getNoise(i, j, scale, 4);
                double riverVal = Math.abs(elevationNoise.getNoise(i, j, 0.04, -2) - 0.5);
                
                this.mapGrid[i][j] = (byte) TileType.getTileTypeFromFloat(elevation, riverVal).ordinal();
            }
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
    public TileType getTileType(int x, int y) {
        final int index = this.mapGrid[x][y];
        return TileType.CACHED_VALUES[index];
    }

    /**
     * changes a tile type
     * @param position .
     * @param tileType .
     */
    public void setTileType(int x, int y, TileType tileType) {

        if(ConstructionRules.canBuild(this.getTileType(x, y), tileType)) {
            this.mapGrid[x][y] = (byte) tileType.ordinal();
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

        for(int i = 0; i < this.width; i++) {
            for(int j = 0; j < this.height; j++) {

                final int index = this.mapGrid[i][j];

                if(TileType.values()[index].equals(tile)) {
                    count++;
                }
            }
        }
        
        return count;
    }
}
