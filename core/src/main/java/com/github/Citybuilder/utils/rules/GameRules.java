package com.github.citybuilder.utils.rules;

public class GameRules {

    // time
    private float tickInterval;
    private float gameSpeed;

    public GameRules() {}

    public float getTickInterval() {return this.tickInterval;}
    public void setTickInterval(float tickInterval) {this.tickInterval = tickInterval;}

    public float getGameSpeed() {return gameSpeed;}
    public void setGameSpeed(float gameSpeed) {this.gameSpeed = gameSpeed;}

}
