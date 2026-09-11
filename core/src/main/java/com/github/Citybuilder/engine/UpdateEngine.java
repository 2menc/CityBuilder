package com.github.citybuilder.engine;

import java.util.ArrayList;
import java.util.List;

import com.github.citybuilder.utils.RuleLoader;
import com.github.citybuilder.utils.Tickable;

/**
 * service that manages all {@link Tickable} implementations
 * Observator pattern: notifies all {@link Tickable} implementations after every tick
 */
public class UpdateEngine {

    private final List<Tickable> tickables;

    private long currentTick;

    /** time for a single tick*/
    private float tickInterval;

    /** spped multiplier */
    private float gameSpeed;

    private float timeAccumulator;

    public UpdateEngine() {
        this.currentTick = 0;
        this.timeAccumulator = 0f;
        this.tickInterval = RuleLoader.RULES.getTickInterval();
        this.gameSpeed = RuleLoader.RULES.getGameSpeed();

        this.tickables = new ArrayList<>();
    }

    /**
     * registers a new {@link Tickable}
     * @param newElement the new Tickable impl.
     */
    public void register(Tickable newElement) {
        this.tickables.add(newElement); 
    }

    public void update(float deltaTime) {

        if(this.gameSpeed <= 0) {
            return;
        }
        
        this.timeAccumulator += (deltaTime * this.gameSpeed);

        while(this.timeAccumulator >= this.tickInterval) {  // checks if a tick is passed

            this.currentTick++;
            this.timeAccumulator -= this.tickInterval;
        
            // notifies all Tickables
            for(var t: this.tickables) {
                t.onTick(this.currentTick);
            }
        }
    }

}
