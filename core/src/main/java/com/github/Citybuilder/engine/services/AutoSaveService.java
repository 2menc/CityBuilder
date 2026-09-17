package com.github.citybuilder.engine.services;

import com.github.citybuilder.model.map.WorldMap;
import com.github.citybuilder.rules.GameRules;
import com.github.citybuilder.utils.files.AutoSaver;
import com.github.citybuilder.utils.files.RuleLoader;

public class AutoSaveService {

    private final Thread autoSaveThread;

    private final GameRules gameRules;
    private final WorldMap map;
    private final String mapName;

    public AutoSaveService(GameRules ruleLoader, WorldMap map, String mapName) {

        this.gameRules = ruleLoader;
        this.mapName = mapName;
        this.map = map;

        this.autoSaveThread = new Thread(this::run);
        this.autoSaveThread.setDaemon(true);
        this.autoSaveThread.start(); 
    }

    private void run() {
    
        try {
           while(true) {
                Thread.sleep(RuleLoader.RULES.getAutoSaveTime());
                this.saveGame();
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public void saveGame() {
        AutoSaver.saveGame(gameRules, map, mapName);
    }


}


