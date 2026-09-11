package com.github.citybuilder.view.hud;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
        final List<TextButton> tilesButtonsList = new ArrayList<>();

        for(var type : TileType.values()) {
            
            String buttonText = type.getFormattedName() + "\n" +
                                "cost: " + type.getPrice() + " $\n" +
                                "expenses: " + type.getPricePerWeek() + " $/w";
                                
            TextButton button = new TextButton(buttonText, tilesButtonStyle);

            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    buildManager.setSelectedTileType(type);
                }
            });

            toolbar.add(button).pad(10).minWidth(120);
        }
        
        // infopanel buttons
        this.balanceLabel = new Label("BALANCE", balanceLabelStyle);

        for(var b: tilesButtonsList) {
            toolbar.add(b);
        }
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
