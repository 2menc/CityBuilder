package com.github.citybuilder.engine.services;

import java.util.ArrayList;
import java.util.List;

import com.github.citybuilder.model.ZoneBlock;
import com.github.citybuilder.utils.Tickable;

public class PopulationService implements Tickable{

    public static final int TAX_PER_CITIZEN = 2;

    private final List<ZoneBlock> blocksList;
    
    private long totalCitizens;
    
    private FinancialService financialService;

    public PopulationService(long totalCitizens) {

        this.totalCitizens = totalCitizens;

        this.blocksList = new ArrayList<>();
    }

    @Override
    public void onTick(long currentTick) {

        long peopleList = 0;
        long totalIncome = 0; 

        for(ZoneBlock zb : this.blocksList) {
            zb.updateZone();
            peopleList += zb.getPeople();
            
            totalIncome += zb.getIncome(); 
        }

        if(peopleList != totalCitizens) {
            this.totalCitizens = peopleList;
        }

        this.financialService.setIncomePerCycle(totalIncome);
    }
    /**
     * gets citizen's total taxes ($/week)
     * @return the taxes
     */
    public long getTotalCitizenTaxesAmount() {

        int total = 0;

        for(var block : this.blocksList) {

            int people = block.getPeople();

            total += people * PopulationService.TAX_PER_CITIZEN;
        }
    
        return total;        
    }

    public void addNewBlock(ZoneBlock block) {
        this.blocksList.add(block);
    }
    public void removeBlock(ZoneBlock block) {
        
        for(var zb : this.blocksList) {
            if(block == zb) {
                final int index = this.blocksList.indexOf(block);
                this.blocksList.remove(index);
            }
        }
    }

    public List<ZoneBlock> getBlocks() { return this.blocksList; }

    public void registerBlock(ZoneBlock block) { 

        this.blocksList.add(block); 
    }

    public void addFinancialService(FinancialService financialService) {
        
        this.financialService = financialService;
    }
}
