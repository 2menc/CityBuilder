package com.github.citybuilder.utils;

public enum Paths {

    BUILDING_RULES("gameRules/notValidTileTypesCoupling.yaml"),
    GAME_RULES("gameRules/gameRules.yaml");

    final String path;

    private Paths(String path) {
        this.path = path;
    }

    public String get() {
        return this.path;
    }
}
