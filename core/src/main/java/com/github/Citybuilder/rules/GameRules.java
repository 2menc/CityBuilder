package com.github.citybuilder.rules;

/**
 * dao class for game rules
 */
public class GameRules {

    private final static int MILLIS_IN_A_SECOND = 1000;

    // time
    private float tickInterval;
    private float gameSpeed;
    private int finance_ticksPerCycle;
    private long startingBalance;
    private int mapWidth;
    private int mapHeight;
    private float populationExpansionFactor;
    private int autoSaveTime;

    public GameRules() {}

    public float getTickInterval() {return this.tickInterval;}
    public void setTickInterval(float tickInterval) {this.tickInterval = tickInterval;}

    public float getGameSpeed() {return gameSpeed;}
    public void setGameSpeed(float gameSpeed) {this.gameSpeed = gameSpeed;}

    public int getFinance_ticksPerCycle() {return finance_ticksPerCycle;}
    public void setFinance_ticksPerCycle(int finance_ticksPerCycle) {this.finance_ticksPerCycle = finance_ticksPerCycle;}

    public long getStartingBalance() {return startingBalance;}
    public void setStartingBalance(long startingBalance) {this.startingBalance = startingBalance;}

    public int getMapWidth() {return mapWidth;}
    public void setMapWidth(int mapWidth) {this.mapWidth = mapWidth;}

    public int getMapHeight() {return mapHeight;}
    public void setMapHeight(int mapHeight) {this.mapHeight = mapHeight;}

    public float getPopulationExpansionFactor() {return populationExpansionFactor;}
    public void setPopulationExpansionFactor(float populationExpansionFactor) {this.populationExpansionFactor = populationExpansionFactor;}

    public int getAutoSaveTime() {return autoSaveTime * MILLIS_IN_A_SECOND;}
    public void setAutoSaveTime(int autoSaveTime) {this.autoSaveTime = autoSaveTime;}

}
