package com.github.citybuilder.view.hud;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.citybuilder.engine.BuildManager;
import com.github.citybuilder.engine.services.FinancialService;
import com.github.citybuilder.engine.services.TimeService;

/**
 * a class that represents the Heads-Up Display overlay for the game.
 * It displays information about the player's resources and provides controls for the game.
 */
public class HUDoverlay {

    private final Stage stage;

    private final InfoBar infoBar;
    private final ToolBar toolBar;

    private TextButton bulldozerButton;

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
        final Label.LabelStyle expendesLabelStyle = new Label.LabelStyle(balanceFont, Color.CORAL);
        final Label.LabelStyle incomeLabelStyle = new Label.LabelStyle(balanceFont, Color.FOREST);
        final Label.LabelStyle normalStyle = new Label.LabelStyle(balanceFont, Color.WHITE);
        
        // textures
        final Pixmap toolbarPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        toolbarPixmap.setColor(Color.DARK_GRAY);
        toolbarPixmap.fill();
        tilesButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(toolbarPixmap)));
        final TextureRegionDrawable toolbarBackground = new TextureRegionDrawable(new TextureRegion(new Texture(toolbarPixmap)));

        // ACTUAL STAGE //

        this.bulldozerButton = new TextButton("Bulldozer", tilesButtonStyle);

        this.stage = new Stage(new ScreenViewport()); //independent from the map viewport

        final Table topTable = new Table();
        topTable.setFillParent(true);
        topTable.top();

        final Table bottomTable = new Table();
        bottomTable.setFillParent(true);
        bottomTable.bottom();

        this.infoBar = new InfoBar(
            this, balanceLabelStyle, 
            expendesLabelStyle, incomeLabelStyle, 
            toolbarBackground, tilesButtonStyle, 
            buildManager, this.bulldozerButton,
            normalStyle
        );
        this.toolBar = new ToolBar(
            this, tilesButtonStyle, 
            toolbarBackground, buildManager,
            bulldozerButton
         );

        topTable.add(infoBar).expandX().fillX();
        bottomTable.add(toolBar);

        stage.addActor(topTable);
        stage.addActor(bottomTable);
    }

    /**
     * Updates the HUD overlay with the current balance, income, and expenses.
     * @param balance
     * @param income
     * @param expenses
     */
    public void updateHUD(
        FinancialService financialService,
        TimeService timeService
    ) {

        if (financialService.getLastKnownBalance() != financialService.getBalance()
            || financialService.getLastKnownIncome() != financialService.getIncomePerCycle()
        ) {
              
            this.infoBar.setBalanceText(financialService.getBalance(), 
                financialService.getIncomePerCycle(), 
                financialService.getExpensesPerCycle()
            );
        }

        this.infoBar.setDate(timeService.getDate());
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


    public void toggleButtonIn(TextButton button, HUDButtonsColors buttonType) { 

        button.getLabel().setColor(buttonType.getIn());    
    }

    public void toggleButtonOut(TextButton button, HUDButtonsColors buttonType) { 
    
        button.getLabel().setColor(buttonType.getOut());    
    }

    public InfoBar getInfoBar() {
        return infoBar;
    }

    public ToolBar getToolBar() {
        return toolBar;
    }


}
