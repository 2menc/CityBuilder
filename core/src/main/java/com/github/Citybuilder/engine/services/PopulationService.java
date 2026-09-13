package com.github.citybuilder.engine.services;

import com.github.citybuilder.utils.Tickable;
import com.github.citybuilder.utils.math.StructureRandomAmountGenerator;

public class PopulationService implements Tickable{

    public static final int TAX_PER_CITIZER = 2;
    
    private long totalCitizens;

    private FinancialService financialService;

    public PopulationService(long totalCitizens) {
        this.totalCitizens = totalCitizens;
    }

    @Override
    public void onTick(long currentTick) {
        ////
    }

    public long getTotalCitizenTaxesAmount() {
        return this.totalCitizens * TAX_PER_CITIZER;
    }

    public void addHouse() {
        this.addCitizens(StructureRandomAmountGenerator.calculateCitizensInAHouse());
    }
    public void removeHouse() {
        this.removeCitizens(StructureRandomAmountGenerator.calculateCitizensInAHouse());
    }

    private void addCitizens(int amount) {
        this.totalCitizens += amount;
    
        this.financialService.addWeeklyIncome((long) (amount * TAX_PER_CITIZER));
    }
    private void removeCitizens(int amount) {
        final long newAmount = this.totalCitizens - amount;

        if(newAmount <= 0) {
            this.totalCitizens = 0;
        } else {
            this.totalCitizens = newAmount;
        }
        this.financialService.removeWeeklyIncome(amount * TAX_PER_CITIZER);
    }

    public void addFinancialService(FinancialService financialService) {
        this.financialService = financialService;
    }

}
