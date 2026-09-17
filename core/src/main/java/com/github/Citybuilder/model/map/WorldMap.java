package com.github.citybuilder.model.map;

import com.github.citybuilder.rules.ConstructionRules;
import com.github.citybuilder.utils.math.*;

/**
 * A class that models the game world map.
 */
public class WorldMap {

    private final int width;
    private final int height;

    private final byte[][] mapGrid;
    private final byte[][] initialMapGrid;

    public WorldMap(int width, int height) {
        this.width = width;
        this.height = height;

        this.mapGrid = new byte[(int) width][(int) height];  
        this.createMap();      
        this.initialMapGrid = new byte[(int) width][(int) height];  
        this.cloneMap();
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

        if (!isValid(x, y)) {
            return TileType.SELVATIC_GRASS; // default tile
        }

        final int index = this.mapGrid[x][y];
        return TileType.CACHED_VALUES[index];
    }

    /**
     * changes a tile type
     * @param position .
     * @param tileType .
     */
    public void setTileType(int x, int y, TileType tileType) {

        if (!isValid(x, y)) {return;}

        if(ConstructionRules.canBuild(this.getTileType(x, y), tileType)) {
            this.mapGrid[x][y] = (byte) tileType.ordinal();
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
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

    private void createMap() {

        double scale = 0.03;

        final NoiseGenerator elevationNoise = new NoiseGenerator();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                double elevation = elevationNoise.getNoise(i, j, scale, 4);
                double riverVal = Math.abs(elevationNoise.getNoise(i, j, 0.04, -2) - 0.5);
                
                this.mapGrid[i][j] = (byte) TileType.getTileTypeFromFloat(elevation, riverVal).ordinal();
            }
        }
    }

    private void cloneMap() {
        for(int i = 0; i < this.width; i++) {
            for(int j = 0; j < this.height; j++) {
                this.initialMapGrid[i][j] = this.mapGrid[i][j];
            }
        }
    }

    public TileType getOriginalTileType(int x, int y) {
        final int index = this.initialMapGrid[x][y];
        return TileType.CACHED_VALUES[index];
    }

    /**
     * searches around the (x, y) for the specified tileToSearch
     * @param x
     * @param y
     * @param tileToSearch
     * @return true if the specified tile is in prximity of (x, y) (order: up, down, dx, sx)
     */
    public boolean isNearTileType(int x, int y, TileType tileToSearch) {

        return (
            getTileType(x, y-1) == tileToSearch ||
            getTileType(x, y+1) == tileToSearch ||
            getTileType(x+1, y) == tileToSearch ||
            getTileType(x-1, y) == tileToSearch
        );
    }

    public byte[][] getMap() {return  this.mapGrid;}
    public byte[][] getInitialMap() {return  this.initialMapGrid;}
}
