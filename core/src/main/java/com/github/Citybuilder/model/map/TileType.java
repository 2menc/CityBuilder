package com.github.citybuilder.model.map;

public enum TileType {

    GRASS(10),
    DIRT(5),
    ROAD(50),
    WATER(25);

    private final long price;

    private TileType(long price) {
        this.price = price;
    } 

    public long getPrice() {
        return this.price;
    }

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
