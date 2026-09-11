package com.github.citybuilder.model.map;

import com.badlogic.gdx.graphics.Color;

public enum TileType {

    SELVATIC_GRASS(12, 0, Color.FOREST),
    GRASS(6, 1, Color.OLIVE),
    DIRT(5, 0, Color.BROWN),
    ROAD(50, 3, Color.GRAY),
    WATER(25, 0, Color.CYAN);

    private final long price;
    private final long pricePerWeek;
    private final Color color;

    private TileType(long price, long pricePerWeek, Color color) {
        this.price = price;
        this.pricePerWeek = pricePerWeek;
        this.color = color;
    } 

    public long getPrice() {
        return this.price;
    }

    public long getPricePerWeek() {
        return this.pricePerWeek;
    }

    public Color getColor() {
        return this.color;
    }

    public static TileType getTileTypeFromFloat(double elevation, double riverIntensity) {
        
        if(riverIntensity < 0.03 || elevation < 0.35) {
            return TileType.WATER;  
        } 
        
        if(elevation < 0.42) {
            return TileType.DIRT;
        } 
        
        return TileType.SELVATIC_GRASS;
    } 

    public String getFormattedName() {

        String[] words = this.name().split("_");
        StringBuilder formatted = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                formatted.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        
        return formatted.toString().trim();
    }
}
