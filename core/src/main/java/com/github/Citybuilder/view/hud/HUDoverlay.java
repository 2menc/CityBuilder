package com.github.citybuilder.view.hud;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
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

    private Label balanceLabel;

    public HUDoverlay(BuildManager buildManager) {

        // font texture
        final BitmapFont tilesFont = new BitmapFont();
        final TextButton.TextButtonStyle tilesButtonStyle = new TextButton.TextButtonStyle();
        tilesButtonStyle.font = tilesFont;
        tilesButtonStyle.fontColor = Color.WHITE;

        final BitmapFont balanceFont = new BitmapFont();
        final TextField.TextFieldStyle balanceTextStyle = new TextField.TextFieldStyle();
        balanceTextStyle.font = balanceFont;
        balanceTextStyle.fontColor = Color.GOLD;

        final Label.LabelStyle balanceLabelStyle = new Label.LabelStyle(balanceFont, Color.GOLD);
        
        // textures
        final Pixmap toolbarPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        toolbarPixmap.setColor(Color.GRAY);
        toolbarPixmap.fill();
        tilesButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(toolbarPixmap)));
        final TextureRegionDrawable toolbarBackground = new TextureRegionDrawable(new TextureRegion(new Texture(toolbarPixmap)));

        // ACTUAL STAGE //
        this.stage = new Stage(new ScreenViewport()); //independent from the map viewport

        final Table topTable = new Table();
        topTable.setFillParent(true);
        topTable.top().right();

        final Table bottomTable = new Table();
        bottomTable.setFillParent(true);
        bottomTable.bottom();

        final Table infoPanel = new Table();
        infoPanel.setBackground(toolbarBackground);

        final Table toolbar = new Table();
        toolbar.setBackground(toolbarBackground);


        // toolbar buttons
        final TextButton grassButton = new TextButton("Grass\n" + TileType.GRASS.getPrice() + "$", tilesButtonStyle);
        grassButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buildManager.setSelectedTileType(TileType.GRASS);
            }
        });

        final TextButton dirtButton = new TextButton("Dirt\n" + TileType.DIRT.getPrice() + "$", tilesButtonStyle);
        dirtButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buildManager.setSelectedTileType(TileType.DIRT);
            }
        });

        final TextButton waterButton = new TextButton("Water\n" + TileType.WATER.getPrice() + "$", tilesButtonStyle);
        waterButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buildManager.setSelectedTileType(TileType.WATER);
            }
        });

        final TextButton roadButton = new TextButton("Road\n" + TileType.ROAD.getPrice() + "$", tilesButtonStyle);
        roadButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buildManager.setSelectedTileType(TileType.ROAD);
            }
        });

        // infopanel buttons
         this.balanceLabel = new Label("BALANCE", balanceLabelStyle);

        toolbar.add(grassButton).pad(8);
        toolbar.add(dirtButton).pad(8);
        toolbar.add(waterButton).pad(8);
        toolbar.add(roadButton).pad(8);  

        infoPanel.add(balanceLabel);

        topTable.add(infoPanel);
        bottomTable.add(toolbar);

        stage.addActor(topTable);
        stage.addActor(bottomTable);
    }

    public void updateHUD(long balance) {
        this.balanceLabel.setText(Long.toString(balance) + "$");
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
