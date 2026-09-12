package com.github.citybuilder.model.map;

/**
 * An enum representing different types of tiles in the game.
 */
public enum TileType {

    SELVATIC_GRASS(6, 0),
    GRASS(6, 0),
    DIRT(5, 0),
    SAND(3, 0),
    ROAD(50, 4),
    CONCRETE(18, 2),
    WOOD(10, 6),
    WATER(15, 0);

    public final static TileType[] CACHED_VALUES = values();

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
    
    public String getTexturePath() {
        
        return "textures/" + this.name().toLowerCase() + ".png";
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
