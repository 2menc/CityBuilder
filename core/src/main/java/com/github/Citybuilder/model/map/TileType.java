package com.github.citybuilder.model.map;

public enum TileType {

    GRASS(10, 0),
    DIRT(5, 0),
    ROAD(50, 3),
    WATER(25, 0);

    private final long price;
    private final long pricePerWeek;

    private TileType(long price, long pricePerWeek) {
        this.price = price;
        this.pricePerWeek = pricePerWeek;
    } 

    public long getPrice() {
        return this.price;
    }

    public long getPricePerWeek() {
        return this.pricePerWeek;
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
