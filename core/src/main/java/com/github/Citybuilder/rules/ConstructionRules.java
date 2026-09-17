package com.github.citybuilder.rules;

import java.util.*;

import com.github.citybuilder.model.map.TileType;
import com.github.citybuilder.utils.files.RuleLoader;

/**
 * A class that contains the rules for building structures on the game map.
 */
public class ConstructionRules {

    private static final Map<TileType, Set<TileType>> notValidBuildMap = RuleLoader.getNotValidBuildMap();

    /**
     * checks if a structure can be built in the specified tile
     * @param terrain
     * @param building
     * @return true if it can be built
     */
    public static boolean canBuild(TileType terrain, TileType building) {
        
        return ! notValidBuildMap.get(terrain).contains(building);
    }

}
