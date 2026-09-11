package com.github.citybuilder.engine;

import com.github.citybuilder.model.map.TileType;
import com.github.citybuilder.model.map.WorldMap;

public class BuildManager {

    private final WorldMap map;

    private TileType selectedTileType;

    public BuildManager(WorldMap map) {
        this.map = map;

        this.selectedTileType = TileType.ROAD; //default
    }

    /**
     * builds the desired {@link TileType} in the specified tile
     * @param x .
     * @param y .
     * @param tile .
     */
    public void buildAt(int x, int y) {
        if(map.isValid(x, y)) {
            map.setTileType(x, y, this.selectedTileType);
        }
    }

    public void selectTileType(TileType tile) {
        this.selectedTileType = tile;
    }
}
