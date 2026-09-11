package com.github.citybuilder.engine;

import com.github.citybuilder.model.map.TileType;
import com.github.citybuilder.model.map.WorldMap;

public class BuildManager {

    private final WorldMap map;

    public BuildManager(WorldMap map) {
        this.map = map;
    }

    /**
     * builds the desired {@link TileType} in the specified tile
     * @param x .
     * @param y .
     * @param tile .
     */
    public void buildAt(int x, int y, TileType tile) {
        if(map.isValid(x, y)) {
            map.setTileType(x, y, tile);
        }
    }
}
