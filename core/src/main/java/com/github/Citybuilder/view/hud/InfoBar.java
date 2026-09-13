package com.github.citybuilder.view.hud;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.github.citybuilder.engine.BuildManager;

public class InfoBar extends Table {

    private final Label balanceLabel;
    private final Label expendesLabel;
    private final Label incomeLabel;

    private final TextButton pauseButton;
    private final TextButton x1Button;
    private final TextButton x3Button;
    private final TextButton x5Button;

    private final TextButton bulldozerButton;

    public InfoBar(
        HUDoverlay parent, 
        LabelStyle balanceLabelStyle, 
        LabelStyle expendesLabelStyle,
        LabelStyle incomeLabelStyle,
        TextureRegionDrawable background, 
        TextButtonStyle tilesButtonStyle,
        BuildManager buildManager,
        TextButton bulldozerButton
    ) {

        this.setBackground(background);

        this.balanceLabel = new Label("BALANCE", balanceLabelStyle);
        this.expendesLabel = new Label("TAXES", expendesLabelStyle);
        this.incomeLabel = new Label("INCOME", incomeLabelStyle);
        this.pauseButton = new TextButton("| | / I>", tilesButtonStyle);
        parent.toggleButtonOut(pauseButton, HUDButtonsColors.GAME_SPEED);
        this.x1Button = new TextButton("x1", tilesButtonStyle);
        parent.toggleButtonOut(x1Button, HUDButtonsColors.GAME_SPEED);
        this.x3Button = new TextButton("x3", tilesButtonStyle);
        parent.toggleButtonOut(x3Button, HUDButtonsColors.GAME_SPEED);
        this.x5Button = new TextButton("x5", tilesButtonStyle);   
        parent.toggleButtonOut(x5Button, HUDButtonsColors.GAME_SPEED);  
        this.bulldozerButton = bulldozerButton;
        parent.toggleButtonOut(bulldozerButton, HUDButtonsColors.BULLDOZER);

        // dd buttons
        this.add(pauseButton).pad(8);
        this.add(x1Button).pad(8);
        this.add(x3Button).pad(8);
        this.add(x5Button).pad(8);

        this.add(bulldozerButton).center().expandX();

        this.add(incomeLabel).right().expandX();
        this.add(expendesLabel).pad(8);
        this.add(balanceLabel).pad(8);

    }    


    public void ifRequestedToTogglePauseGame(ChangeListener cl) {this.pauseButton.addListener(cl);}
    public void ifRequestedTox1(ChangeListener cl) {this.x1Button.addListener(cl);}
    public void ifRequestedTox3(ChangeListener cl) {this.x3Button.addListener(cl);}
    public void ifRequestedTox5(ChangeListener cl) {this.x5Button.addListener(cl);}
    public void ifRequestedBullDozer(ChangeListener cl) {this.bulldozerButton.addListener(cl);}


    public void setBalanceText(long balance, long income, long expenses) {

        this.balanceLabel.setText(Long.toString(balance) + "$");

        this.incomeLabel.setText(Long.toString(income) + " $/w");
        this.expendesLabel.setText(Long.toString(expenses) + " $/w");

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

    public TextButton getBulldozerButton() {
        return bulldozerButton;
    }

}

