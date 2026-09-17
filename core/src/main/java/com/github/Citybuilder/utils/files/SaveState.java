package com.github.citybuilder.utils.files;

import com.github.citybuilder.rules.GameRules;

public class SaveState {

    private GameRules rules;
    private GameMap map;
    private String worldName;

    public SaveState() { }

    public SaveState(GameRules rules, GameMap map, String worldName) {
        
        this.rules = rules;
        this.map = map;
        this.worldName = worldName;
    }
    
    public GameRules getRules() {
        return rules;
    }

    public void setRules(GameRules rules) {
        this.rules = rules;
    }


    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public GameMap getMap() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

}
