package com.github.citybuilder.utils;

public enum Paths {

    GAMERULES_PATH("gameRules/notValidTileTypesCoupling.yaml");

    final String path;

    private Paths(String path) {
        this.path = path;
    }

    public String get() {
        return this.path;
    }
}
