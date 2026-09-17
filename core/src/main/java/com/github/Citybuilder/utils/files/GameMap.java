package com.github.citybuilder.utils.files;

import com.github.citybuilder.model.map.WorldMap;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List; 

public class GameMap {

    public int width;
    public int height;

    public List<byte[]> mapGrid;
    public List<byte[]> initialMapGrid;

    public GameMap() {}


    public GameMap(WorldMap worldMap) {
        this.width = worldMap.getWidth(); 
        this.height = worldMap.getHeight();
        transformMap(worldMap);
    }

    private void transformMap(WorldMap map) {
        this.mapGrid = new LinkedList<>(Arrays.asList(map.getMap()));
        this.initialMapGrid = new LinkedList<>(Arrays.asList(map.getInitialMap()));
    }
}