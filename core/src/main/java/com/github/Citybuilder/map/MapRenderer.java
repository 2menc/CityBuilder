package com.github.Citybuilder.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MapRenderer {

    /** size (in pixels) per tile */
    private final static int TILE_SIZE = 32;

    private final WorldMap map;

    public MapRenderer(WorldMap map) {
        this.map = map;
    }

    public void render(OrthographicCamera camera, ShapeRenderer shapeRenderer) {
        
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    
        for (int i = 0; i < this.map.getWidth(); i++) {
            for (int j = 0; j < this.map.getHeight(); j++) {
                final var tile = map.getTile(i, j);
                final var type = tile.getType();

                switch (type) {
                    case TileType.GRASS:
                        shapeRenderer.setColor(Color.OLIVE);
                        break;
                    case TileType.ROAD:
                        shapeRenderer.setColor(Color.GRAY);
                        break;
                    case TileType.DIRT:
                        shapeRenderer.setColor(Color.BROWN);
                        break;
                    case TileType.WATER:
                        shapeRenderer.setColor(Color.CYAN);
                        break;
                    default:
                        shapeRenderer.setColor(Color.PURPLE);
                }

                shapeRenderer.rect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
        shapeRenderer.end();
    }
}
