package com.github.citybuilder.engine.services;

import java.time.LocalDate;

import com.github.citybuilder.utils.Tickable;
import com.github.citybuilder.utils.files.RuleLoader;

public class TimeService implements Tickable {

    private final static int SECONDS_IN_A_DAY = 60* 60 * 24;
    private final static long START_EPOCH_DAY = LocalDate.of(0, 1, 1).toEpochDay();

    private long secondsPassed;
    private final long ticksPerDay;

    public TimeService() {

        this.ticksPerDay = RuleLoader.RULES.getFinance_ticksPerCycle(); // rinominare idealmente in getTicksPerDay()

        this.secondsPassed = 0;
    }

    @Override
    public void onTick(long currentTick) {

        if(currentTick % this.ticksPerDay == 0) {
            this.secondsPassed += SECONDS_IN_A_DAY;
        }
    }

    public long getSecondsPassed() {
        return secondsPassed;
    }

    public void setSecondsPassed(long secondsPassed) {
        this.secondsPassed = secondsPassed;
    }

    public LocalDate getDate() {

        long daysPassed = this.secondsPassed / SECONDS_IN_A_DAY;

        return LocalDate.ofEpochDay(START_EPOCH_DAY + daysPassed);
    }

}
