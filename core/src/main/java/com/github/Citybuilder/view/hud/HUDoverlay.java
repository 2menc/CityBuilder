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

/**
 * a class that represents the Heads-Up Display overlay for the game.
 * It displays information about the player's resources and provides controls for the game.
 */
public class HUDoverlay {

    private final Stage stage;

    private long lastKnownBalance;

    private final Label balanceLabel;
    private final Label expendesLabel;
    private final Label incomeLabel;

    final TextButton pauseButton;
    final TextButton x1Button;
    final TextButton x3Button;
    final TextButton x5Button;    
    
    final TextButton bullDozerButton;

    public HUDoverlay(BuildManager buildManager) {

        this.lastKnownBalance = -1;

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
        final Label.LabelStyle expendesLabelStyle = new Label.LabelStyle(balanceFont, Color.CORAL);
        final Label.LabelStyle incomeLabelStyle = new Label.LabelStyle(balanceFont, Color.FOREST);
        
        // textures
        final Pixmap toolbarPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        toolbarPixmap.setColor(Color.DARK_GRAY);
        toolbarPixmap.fill();
        tilesButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(toolbarPixmap)));
        final TextureRegionDrawable toolbarBackground = new TextureRegionDrawable(new TextureRegion(new Texture(toolbarPixmap)));

        // ACTUAL STAGE //
        this.stage = new Stage(new ScreenViewport()); //independent from the map viewport

        final Table topTable = new Table();
        topTable.setFillParent(true);
        topTable.top();

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

                    toggleButtonOut(bullDozerButton, HUDButtonsColors.BULLDOZER);
                }
            });

            toolbar.add(button).pad(10).minWidth(120);
        }
        
        // infopanel buttons
        this.balanceLabel = new Label("BALANCE", balanceLabelStyle);
        this.expendesLabel = new Label("TAXES", expendesLabelStyle);
        this.incomeLabel = new Label("INCOME", incomeLabelStyle);
        this.pauseButton = new TextButton("| | / I>", tilesButtonStyle);
        this.toggleButtonOut(pauseButton, HUDButtonsColors.GAME_SPEED);
        this.x1Button = new TextButton("x1", tilesButtonStyle);
        this.toggleButtonOut(x1Button, HUDButtonsColors.GAME_SPEED);
        this.x3Button = new TextButton("x3", tilesButtonStyle);
        this.toggleButtonOut(x3Button, HUDButtonsColors.GAME_SPEED);
        this.x5Button = new TextButton("x5", tilesButtonStyle);   
        this.toggleButtonOut(x5Button, HUDButtonsColors.GAME_SPEED);  
        this.bullDozerButton = new TextButton("Bulldozer", tilesButtonStyle);
        this.toggleButtonOut(bullDozerButton, HUDButtonsColors.BULLDOZER);

        for(var b: tilesButtonsList) {
            toolbar.add(b);
        }

        infoPanel.add(pauseButton).pad(8);
        infoPanel.add(x1Button).pad(8);
        infoPanel.add(x3Button).pad(8);
        infoPanel.add(x5Button).pad(8);

        infoPanel.add(bullDozerButton).center().expandX();

        infoPanel.add(incomeLabel).right().expandX();
        infoPanel.add(expendesLabel).pad(8);
        infoPanel.add(balanceLabel).pad(8);

        topTable.add(infoPanel).expandX().fillX();
        bottomTable.add(toolbar);

        stage.addActor(topTable);
        stage.addActor(bottomTable);
    }

    /**
     * Updates the HUD overlay with the current balance, income, and expenses.
     * @param balance
     * @param income
     * @param expenses
     */
    public void updateHUD(long balance, long income, long expenses) {

        if(this.lastKnownBalance != balance) {
            this.balanceLabel.setText(Long.toString(balance) + "$");
            this.lastKnownBalance = balance;

            this.incomeLabel.setText(Long.toString(income) + " $/w");
            this.expendesLabel.setText(Long.toString(expenses) + " $/w");
        }
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

    public void ifRequestedToTogglePauseGame(ChangeListener cl) {this.pauseButton.addListener(cl);}
    public void ifRequestedTox1(ChangeListener cl) {this.x1Button.addListener(cl);}
    public void ifRequestedTox3(ChangeListener cl) {this.x3Button.addListener(cl);}
    public void ifRequestedTox5(ChangeListener cl) {this.x5Button.addListener(cl);}
    public void ifRequestedBullDozer(ChangeListener cl) {this.bullDozerButton.addListener(cl);}


    public void toggleButtonIn(TextButton button, HUDButtonsColors buttonType) { 

        button.getLabel().setColor(buttonType.getIn());    
    }

    public void toggleButtonOut(TextButton button, HUDButtonsColors buttonType) { 
    
        button.getLabel().setColor(buttonType.getOut());    
    }

    public TextButton getPauseButton() {
        return pauseButton;
    }

    public TextButton getX1Button() {
        return x1Button;
    }

    public TextButton getX3Button() {
        return x3Button;
    }

    public TextButton getX5Button() {
        return x5Button;
    }

    public TextButton getBullDozerButton() {
        return bullDozerButton;
    }

}
