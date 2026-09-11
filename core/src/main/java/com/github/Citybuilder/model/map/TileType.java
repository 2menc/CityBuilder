package com.github.Citybuilder.model.map;

public enum TileType {

    GRASS,
    DIRT,
    ROAD,
    WATER;

    public static TileType getTileTypeFromFloat(double elevation, double riverIntensity) {
        
        if(riverIntensity < 0.03 || elevation < 0.35) {
            return TileType.WATER;  
        } 
        
        if(elevation < 0.42) {
            return TileType.DIRT;
        } 
        
        return TileType.GRASS;
    } 

}
