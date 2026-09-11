package com.github.citybuilder.utils;

/**
 * interface implemented by every service that has to update regolarly
 */
public interface Tickable {

    /**
     * called at every simulation tick
     * @param currentTick progressive game tick number
     */
    void onTick(long currentTick);

}
