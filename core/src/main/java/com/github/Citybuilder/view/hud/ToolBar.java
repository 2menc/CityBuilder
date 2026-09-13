package com.github.citybuilder.view.hud;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.github.citybuilder.engine.BuildManager;
import com.github.citybuilder.model.map.TileType;

public class ToolBar extends Table {

    public ToolBar(HUDoverlay parent, 
        TextButtonStyle style, 
        TextureRegionDrawable background, 
        BuildManager buildManager,
        TextButton bulldozerButton
    ) {

        this.setBackground(background);

        // buttons
        final List<TextButton> tilesButtonsList = new ArrayList<>();

        for(var type : TileType.values()) {
            
            String buttonText = type.getFormattedName() + "\n" +
                                "cost: " + type.getPrice() + " $\n" +
                                "expenses: " + type.getPricePerWeek() + " $/w";
                                
            TextButton button = new TextButton(buttonText, style);
        
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    buildManager.setSelectedTileType(type);

                    parent.toggleButtonOut(bulldozerButton, HUDButtonsColors.BULLDOZER);
                }
            });


            this.add(button).pad(10).minWidth(120);
        }

        for(var b: tilesButtonsList) {
            this.add(b);
        }

    }

}
