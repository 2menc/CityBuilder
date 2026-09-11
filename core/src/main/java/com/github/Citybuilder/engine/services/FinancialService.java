package com.github.citybuilder.engine.services;

import com.github.citybuilder.utils.RuleLoader;
import com.github.citybuilder.utils.Tickable;

public class FinancialService implements Tickable {

    private long balance;
    private long expensesPerCycle;
    private long incomePerCycle;

    /** tells how many tick have to clock for a cycle */
    private final int ticksPerCycle;
    
    public FinancialService(long startingBalance) {
        this.balance = startingBalance;

        this.expensesPerCycle = 0;
        this.incomePerCycle = 0;

        this.ticksPerCycle = RuleLoader.RULES.getFinance_ticksPerCycle();
    }

    @Override
    public void onTick(long currentTick) {

        if(currentTick % ticksPerCycle == 0) {
            final long signedAmount = incomePerCycle - expensesPerCycle;
            this.balance += signedAmount;
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

}
