package com.github.citybuilder.engine;

import com.github.citybuilder.engine.services.FinancialService;
import com.github.citybuilder.model.map.TileType;
import com.github.citybuilder.model.map.WorldMap;
import com.github.citybuilder.rules.ConstructionRules;

public class BuildManager {

    private final WorldMap map;
    private final FinancialService financialService;

    private TileType selectedTileType;

    public BuildManager(WorldMap map, FinancialService financialService) {
        this.map = map;
        this.financialService = financialService;

        this.selectedTileType = TileType.ROAD; //default
    }

    /**
     * builds the desired {@link TileType} in the specified tile
     * @param x .
     * @param y .
     * @param tile .
     */
    public void buildAt(int x, int y) {
        if(map.isValid(x, y) 
                && financialService.canAfford(this.selectedTileType.getPrice()) 
                && map.getTileType(x, y) != this.selectedTileType
                && ConstructionRules.canBuild(map.getTileType(x, y), selectedTileType)) {

            financialService.decreseBalance(this.selectedTileType.getPrice());
            financialService.addWeeklyExpense(this.selectedTileType.getPricePerWeek());
            map.setTileType(x, y, this.selectedTileType);
        }
    }

    public void setSelectedTileType(TileType tile) {
        this.selectedTileType = tile;
    }

    public TileType getSelecTileType() {return this.selectedTileType;}
}
