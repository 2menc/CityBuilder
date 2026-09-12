package com.github.citybuilder.view;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.citybuilder.engine.BuildManager;
import com.github.citybuilder.engine.services.FinancialService;
import com.github.citybuilder.model.map.*;
import com.github.citybuilder.rules.ConstructionRules;

/**
 * A class that renders the game map.
 */
public class MapRenderer {

    /** size (in pixels) per tile */
    private final static int TILE_SIZE = 32;

    private final WorldMap map;
    private final SpriteBatch batch;

    private final Map<TileType, Texture> textureCache;

    public MapRenderer(WorldMap map) {
        this.map = map;
        this.batch = new SpriteBatch();
        this.textureCache = new HashMap<>();

        for(var type: TileType.values()) {
            final Texture tex = new Texture(Gdx.files.internal(type.getTexturePath()));
            textureCache.put(type, tex);
        }
    }

    public void render(OrthographicCamera camera, ShapeRenderer shapeRenderer, int hoverX, int hoverY, FinancialService financialService, BuildManager buildManager) {
        
        batch.setProjectionMatrix(camera.combined);

        if (!batch.isDrawing()) {
            batch.begin();
        }

        try {
            // visible camera space
            float halfViewportWidth = (camera.viewportWidth * camera.zoom) / 2f;
            float halfViewportHeight = (camera.viewportHeight * camera.zoom) / 2f;

            int startX = Math.max(0, (int) ((camera.position.x - halfViewportWidth) / TILE_SIZE));
            int endX = (int) Math.min(map.getWidth(), (int) ((camera.position.x + halfViewportWidth) / TILE_SIZE) + 2);

            int startY = Math.max(0, (int) ((camera.position.y - halfViewportHeight) / TILE_SIZE));
            int endY = (int) Math.min(map.getHeight(), (int) ((camera.position.y + halfViewportHeight) / TILE_SIZE) + 2);

            // only draw visible tiles
            for (int x = startX; x < endX; x++) {
                for (int y = startY; y < endY; y++) {
                    TileType type = map.getTileType(x, y);
                    Texture tileTexture = textureCache.get(type);

                    if (tileTexture != null) {
                        batch.draw(tileTexture, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    }
                }
            }
        } finally {
            if (batch.isDrawing()) {
                batch.end();
            }
        }

        // tiles outlining
        final Color outlineColor = financialService.canAfford(buildManager.getSelecTileType().getPrice()) 
            && ConstructionRules.canBuild(map.getTileType(hoverX, hoverY), buildManager.getSelecTileType()) 
            ? Color.GREEN : Color.RED;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setColor(outlineColor);

        shapeRenderer.rect(hoverX * TILE_SIZE, hoverY * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        shapeRenderer.end();


    }
}

