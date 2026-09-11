package com.github.citybuilder.model.map;

import com.badlogic.gdx.graphics.Color;

public enum TileType {

    SELVATIC_GRASS(6, 0, Color.FOREST),
    GRASS(6, 0, Color.OLIVE),
    DIRT(5, 0, Color.BROWN),
    SAND(3, 0, Color.TAN),
    ROAD(50, 4, Color.DARK_GRAY),
    CONCRETE(18, 2, Color.GRAY),
    WOOD(10, 6, new Color(0.65f, 0.50f, 0.25f, 1f)),
    WATER(15, 0, Color.CYAN);

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
        
        if(riverIntensity < 0.02 || elevation < 0.35) {
            return TileType.WATER;  
        } 
        if((riverIntensity < 0.045 && riverIntensity >= 0.03) 
                || (elevation < 0.4 && elevation >= 0.35)) {
            return TileType.SAND;  
        }         
        if(riverIntensity < 0.1 
                || (elevation > 0.55 && elevation < 0.78)) {
            return TileType.SELVATIC_GRASS;  
        }     
        if(elevation < 0.455 && elevation > 0.4) {
            return TileType.DIRT;
        } 
        
        return TileType.GRASS;
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
