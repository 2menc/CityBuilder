package com.github.citybuilder.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.github.citybuilder.model.map.*;

public class MapRenderer {

    /** size (in pixels) per tile */
    private final static int TILE_SIZE = 32;

    private final WorldMap map;

    public MapRenderer(WorldMap map) {
        this.map = map;
    }

    public void render(OrthographicCamera camera, ShapeRenderer shapeRenderer, int hoverX, int hoverY) {
        
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    
        for (int i = 0; i < this.map.getWidth(); i++) {
            for (int j = 0; j < this.map.getHeight(); j++) {
                final var tile = map.getTile(i, j);

                switch (tile.getType()) {
                    case GRASS -> shapeRenderer.setColor(Color.OLIVE);
                    case DIRT  -> shapeRenderer.setColor(Color.BROWN);
                    case WATER -> shapeRenderer.setColor(Color.CYAN);                    
                    
                    case ROAD  -> shapeRenderer.setColor(Color.GRAY);

                    default -> shapeRenderer.setColor(Color.PURPLE);
                }

                shapeRenderer.rect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
        shapeRenderer.end();

        if(map.isValid(hoverX, hoverY)) {

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.TAN);
            
            shapeRenderer.rect(hoverX * TILE_SIZE, hoverY * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            shapeRenderer.end();
        }
    }
}
