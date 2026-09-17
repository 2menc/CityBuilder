package com.github.citybuilder.engine;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.github.citybuilder.utils.Tickable;
import com.github.citybuilder.utils.files.RuleLoader;
import com.github.citybuilder.view.hud.HUDoverlay;

/**
 * service that manages all {@link Tickable} implementations
 * Observator pattern: notifies all {@link Tickable} implementations after every tick
 */
public class UpdateEngine {


    private final List<Tickable> tickables;

    private long currentTick;

    /** time for a single tick*/
    private float tickInterval;

    private float timeAccumulator;
    private final HUDoverlay hud;

    public UpdateEngine(HUDoverlay hud) {
        this.currentTick = 0;
        this.timeAccumulator = 0f;
        this.tickInterval = RuleLoader.RULES.getTickInterval();
        this.hud = hud;

        this.tickables = new ArrayList<>();

        this.initializeButtonListeners();
    }

    /**
     * registers a new {@link Tickable}
     * @param newElement the new Tickable impl.
     */
    public void register(Tickable newElement) {
        this.tickables.add(newElement); 
    }

    public void update(float deltaTime) {

        if(RuleLoader.RULES.getGameSpeed() <= 0) {
            return;
        }
        
        this.timeAccumulator += (deltaTime * RuleLoader.RULES.getGameSpeed());

        while(this.timeAccumulator >= this.tickInterval) {  // checks if a tick is passed

            this.currentTick++;
            this.timeAccumulator -= this.tickInterval;
        
            // notifies all Tickables
            for(var t: this.tickables) {
                t.onTick(this.currentTick);
            }
        }
    }

    public void setGameSpeed(int multiplier) {

        RuleLoader.RULES.setGameSpeed(multiplier);        
    }

    private void initializeButtonListeners() {

        hud.getInfoBar().ifRequestedToTogglePauseGame(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(RuleLoader.RULES.getGameSpeed() <= 0F) {
                    RuleLoader.RULES.setGameSpeed(1F);
                } else {
                    RuleLoader.RULES.setGameSpeed(0F);
                }
            }
        });
        hud.getInfoBar().ifRequestedTox1(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                RuleLoader.RULES.setGameSpeed(1F);
            }
        });
        hud.getInfoBar().ifRequestedTox3(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                RuleLoader.RULES.setGameSpeed(3F);
            }
        });
        hud.getInfoBar().ifRequestedTox5(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                RuleLoader.RULES.setGameSpeed(5F);
            }
        });
    }    


}
