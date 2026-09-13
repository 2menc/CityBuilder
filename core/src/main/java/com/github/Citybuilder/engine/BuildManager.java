package com.github.citybuilder.engine;

import com.github.citybuilder.engine.services.FinancialService;
import com.github.citybuilder.engine.services.PopulationService;
import com.github.citybuilder.model.map.TileType;
import com.github.citybuilder.model.map.WorldMap;
import com.github.citybuilder.rules.ConstructionRules;


/**
 * A class that manages the building of structures on the game map.
 */
public class BuildManager {

    private final WorldMap map;
    private final FinancialService financialService;
    private final PopulationService populationService;

    private TileType selectedTileType;
    private boolean bullDozerActive;

    public BuildManager(WorldMap map, FinancialService financialService, PopulationService populationService) {
        this.map = map;
        this.financialService = financialService;
        this.populationService = populationService;

        this.selectedTileType = TileType.ROAD; //default
    }

    public void interactAt(int x, int y) {

        if(map.isValid(x, y)) {

            if(this.bullDozerActive) {
                demolishAt(x, y);
            } else {
                if(this.isAZoneSelected()) {
                    this.buildZoneAt(x, y);
                } else {
                    this.buildAt(x, y);
                }
            }
        }
    }

    /**
     * builds the desired zone in (x, y)
     * @param x
     * @param y
     */
    private void buildZoneAt(int x, int y) {
        
        boolean nearRoadOrConcrete = this.map.isNearTileType(x, y, TileType.ROAD) 
            || this.map.isNearTileType(x, y, TileType.CONCRETE);
        boolean isRoadOrConcrete = this.map.getTileType(x, y).equals(TileType.ROAD)
            || this.map.getTileType(x, y).equals(TileType.CONCRETE);

        if (nearRoadOrConcrete && !isRoadOrConcrete) {
            if (this.selectedTileType.equals(TileType.ZONE_RESIDENTIAL) && !TileType.isAZone(this.map.getTileType(x, y))) {
                this.populationService.addHouse();
                this.buildAt(x, y);
            }
        }

    }

    /**
     * builds the desired {@link TileType} in the specified tile
     * @param x .
     * @param y .
     * @param tile .
     */
    private void buildAt(int x, int y) {

        TileType currentType = map.getTileType(x, y);

        if(financialService.canAfford(this.selectedTileType.getPrice()) 
                && map.getTileType(x, y) != this.selectedTileType
                && ConstructionRules.canBuild(map.getTileType(x, y), selectedTileType)) {

            if (currentType.getPricePerWeek() > 0) {
                financialService.removeWeeklyExpense(currentType.getPricePerWeek());
            }

            financialService.decreseBalance(this.selectedTileType.getPrice());
            financialService.addWeeklyExpense(this.selectedTileType.getPricePerWeek());
            
            map.setTileType(x, y, this.selectedTileType);        
        }
    }

    /**
     * removes a placed tile and returns 50% of the price
     * @param x
     * @param y
     */
    private void demolishAt(int x, int y) {

        TileType currentType = map.getTileType(x, y);

        if (currentType == TileType.SELVATIC_GRASS || currentType == TileType.DIRT
                || currentType == TileType.GRASS || currentType == TileType.WATER
                || currentType == TileType.SAND
        ) {
            return;
        }

        long refund = currentType.getPrice() / 2;
        financialService.increaseBalance(refund);

        if (currentType.getPricePerWeek() > 0) {
            financialService.removeWeeklyExpense(currentType.getPricePerWeek());
        }

        map.setTileType(x, y, map.getOriginalTileType(x, y));

        if(this.selectedTileType.equals(TileType.ZONE_RESIDENTIAL)) {
            this.populationService.removeHouse();
        }
    }

    public void enableBulldozer() {
        this.bullDozerActive = true;
    }

    public void disableBulldozer() {
        this.bullDozerActive = false;
    }

    public boolean isBulldozerActive() {
        return  this.bullDozerActive;
    }
    

    public void setSelectedTileType(TileType tile) {
        this.selectedTileType = tile;

        this.bullDozerActive = false;
    }

    public TileType getSelecTileType() {return this.selectedTileType;}

    private boolean isAZoneSelected() {

        return TileType.isAZone(this.selectedTileType);
    }

}
