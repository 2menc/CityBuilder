package com.github.citybuilder.engine.services;

import com.github.citybuilder.utils.RuleLoader;
import com.github.citybuilder.utils.Tickable;

/**
 * A class that manages the financial aspects of the game.
 */
public class FinancialService implements Tickable {

    private long lastKnownBalance = -1;

    private long balance;
    private long expensesPerCycle;
    private long incomePerCycle;

    /** tells how many tick have to clock for a cycle */
    private final int ticksPerCycle;
    private final int ticksPerWeek;
    
    public FinancialService(long startingBalance) {
        this.balance = startingBalance;

        this.expensesPerCycle = 0;
        this.incomePerCycle = 0;

        this.ticksPerCycle = RuleLoader.RULES.getFinance_ticksPerCycle();
        this.ticksPerWeek = ticksPerCycle * 7;
    }

    @Override
    public void onTick(long currentTick) {

        if(currentTick % ticksPerCycle == 0) {
            final long signedAmount = incomePerCycle - expensesPerCycle;
            this.balance += signedAmount;
        }

        if(currentTick % ticksPerWeek == 0) {    // a week just passed
            this.decreseBalance(this.expensesPerCycle);
        }
    }

    public boolean canAfford(long price) {
        return price <= this.balance;
    }

    public boolean decreseBalance(long amount) {
        if(canAfford(amount)) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    public void increaseBalance(long amount) {
        this.balance += amount;
    }

    public void addWeeklyExpense(long pricePerWeek) {

        this.expensesPerCycle += pricePerWeek;

        if(this.expensesPerCycle <= 0) {
            this.expensesPerCycle = 0;
        }
    }
    public void removeWeeklyExpense(long pricePerWeek) {

        this.expensesPerCycle -= pricePerWeek;

        if(this.expensesPerCycle <= 0) {
            this.expensesPerCycle = 0;
        }
    }
    
    public void addWeeklyIncome(long incomePerWeek) {
        this.incomePerCycle += incomePerWeek;

        if(this.incomePerCycle <= 0) {
            this.incomePerCycle = 0;
        }
    }
    
    public long getBalance() {
        return this.balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getExpensesPerCycle() {
        return expensesPerCycle;
    }

    public void setExpensesPerCycle(long expensesPerCycle) {
        this.expensesPerCycle = expensesPerCycle;
    }

    public long getIncomePerCycle() {
        return incomePerCycle;
    }

    public void setIncomePerCycle(long incomePerCycle) {
        this.incomePerCycle = incomePerCycle;
    }

    public int getTicksPerCycle() {
        return ticksPerCycle;
    }

    public void updateLastKnownBalance(long balance) {
        this.lastKnownBalance = balance;
    }
    public long getLastKnownBalance() {
        return  this.lastKnownBalance;
    }
}
