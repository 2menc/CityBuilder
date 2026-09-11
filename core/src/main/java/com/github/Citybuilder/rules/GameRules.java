package com.github.citybuilder.rules;

public class GameRules {

    // time
    private float tickInterval;
    private float gameSpeed;
    private int finance_ticksPerCycle;
    private long startingBalance;

    public GameRules() {}

    public float getTickInterval() {return this.tickInterval;}
    public void setTickInterval(float tickInterval) {this.tickInterval = tickInterval;}

    public float getGameSpeed() {return gameSpeed;}
    public void setGameSpeed(float gameSpeed) {this.gameSpeed = gameSpeed;}

    public int getFinance_ticksPerCycle() {return finance_ticksPerCycle;}
    public void setFinance_ticksPerCycle(int finance_ticksPerCycle) {this.finance_ticksPerCycle = finance_ticksPerCycle;}

    public long getStartingBalance() {return startingBalance;}
    public void setStartingBalance(long startingBalance) {this.startingBalance = startingBalance;}

}
