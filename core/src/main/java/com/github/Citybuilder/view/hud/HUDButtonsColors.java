package com.github.citybuilder.view.hud;

import com.badlogic.gdx.graphics.Color;

public enum HUDButtonsColors {

    GAME_SPEED("3F3F3FFF", "BFBFBFFF"),     // light gray

    BULLDOZER("8B4513FF", "FFA500FF");  // orange

    private final String in;
    private final String out;

    private HUDButtonsColors(String in, String out) {
        this.in = in;
        this.out = out;
    }

    public Color getIn() {
        return Color.valueOf(this.in);
    }

    public Color getOut() {
        return Color.valueOf(this.out);
    }

}
