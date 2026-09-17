package com.github.citybuilder.model;

/**
 * interface that defines a zone block: set of cells in wich there are
 * residential/construction/ ... zones.
 * 
 * every zone calculates individually his
 * income/expenses and sends them to the {@link PopulationService}
 */
public interface ZoneBlock{

    void addTileToBlock(long x, long y);

    void removeTileFromBlock(long x, long y);

    boolean isAdjacentTo(long x, long y);

    boolean containsTile(long x, long y);

    void updateZone();

    int getPeople();

    boolean isEmpty();

    int getIncome();

}
