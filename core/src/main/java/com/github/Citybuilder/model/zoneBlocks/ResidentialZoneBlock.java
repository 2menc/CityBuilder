package com.github.citybuilder.model.zoneBlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

import com.github.citybuilder.engine.services.PopulationService;
import com.github.citybuilder.model.ZoneBlock;
import com.github.citybuilder.utils.Pair;
import com.github.citybuilder.utils.files.RuleLoader;
import com.github.citybuilder.utils.math.StructureRandomAmountGenerator;

public class ResidentialZoneBlock implements ZoneBlock {

    private final RandomGenerator randomGenerator;

    private List<Pair<Long, Long>> grid;

    /** every element in the array represents an house, with $n$ inshabitants */
    private final List<Integer> houseMembersList;

    public ResidentialZoneBlock(int firstX, int firstY) {

        this.grid = new ArrayList<>();

        this.randomGenerator = new Random();

        this.houseMembersList = new ArrayList<>();

        this.addTileToBlock(firstX, firstY);
    }

    @Override
    public void updateZone() {

        if(randomGenerator.nextFloat()%100 <= RuleLoader.RULES.getPopulationExpansionFactor()) { // % probability of creating a new house every tick
            this.buildHouse();
        }
    }

    @Override 
    public void addTileToBlock(long x, long y) {

        this.grid.add(new Pair<>(x, y));
    }    

    @Override 
    public void removeTileFromBlock(long x, long y) {
        
        this.grid.remove(new Pair<>(x, y));
    }

    @Override 
    public int getPeople() {

        int total = 0;

        for(int houseMembers : this.houseMembersList) {

            total += houseMembers;
        }
    
        return total;        

    }

    @Override
    public boolean isAdjacentTo(long x, long y) {

        for (Pair<Long, Long> p : grid) {
                long px = p.getX();
                long py = p.getY();
                
                if ((Math.abs(px - x) == 1 && py == y) || (Math.abs(py - y) == 1 && px == x)) {
                    return true;
                }
            }
            return false;
    }

    private boolean canBuildHouse() {
        
        return this.grid.size() > this.houseMembersList.size();
    }

    private void buildHouse() {
        
        if(this.canBuildHouse()) {
            this.houseMembersList.add(StructureRandomAmountGenerator.calculateCitizensInAHouse());
        }
    }

    @Override
    public boolean containsTile(long x, long y) {

        for(var p : this.grid) {
            if(p.getX().equals(x) && p.getY().equals(y)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isEmpty() {

        return this.getPeople() == 0;
    }

    @Override
    public int getIncome() {

        return this.getPeople() * PopulationService.TAX_PER_CITIZEN;
    }


}
