package com.github.citybuilder.view.hud;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.citybuilder.engine.BuildManager;
import com.github.citybuilder.model.map.TileType;

public class HUDoverlay {

    private final Stage stage;

    public HUDoverlay(BuildManager buildManager) {

        // font texture
        final BitmapFont font = new BitmapFont();
        final TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        style.fontColor = Color.WHITE;

        // textures
        final Pixmap toolbarPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        toolbarPixmap.setColor(Color.GRAY);
        toolbarPixmap.fill();
        style.up = new TextureRegionDrawable(new TextureRegion(new Texture(toolbarPixmap)));
        final TextureRegionDrawable toolbarBackground = new TextureRegionDrawable(new TextureRegion(new Texture(toolbarPixmap)));

        // ACTUAL STAGE //
        this.stage = new Stage(new ScreenViewport()); //independent from the map viewport

        /* root table: fake table that fills the entire screen, only for aligning the other hud tables in a layout */
        final Table rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.bottom();
        stage.addActor(rootTable);

        final Table toolbar = new Table();
        toolbar.setBackground(toolbarBackground);

        final TextButton grassButton = new TextButton("Grass", style);
        grassButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buildManager.setSelectedTileType(TileType.GRASS);
            }
        });

        TextButton dirtButton = new TextButton("Dirt", style);
        dirtButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buildManager.setSelectedTileType(TileType.DIRT);
            }
        });

        TextButton waterButton = new TextButton("Water", style);
        waterButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buildManager.setSelectedTileType(TileType.WATER);
            }
        });

        TextButton roadButton = new TextButton("Road", style);
        roadButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buildManager.setSelectedTileType(TileType.ROAD);
            }
        });

        toolbar.add(grassButton).pad(8);
        toolbar.add(dirtButton).pad(8);
        toolbar.add(waterButton).pad(8);
        toolbar.add(roadButton).pad(8);  

        rootTable.add(toolbar);

        stage.addActor(rootTable);
    }

    public void render() {
        stage.getViewport().apply();
        stage.act();
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose() {
        stage.dispose();
    }
}
