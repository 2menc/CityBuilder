package com.github.citybuilder.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * A class that handles input events for the game.
 */
public class InputEngine extends InputAdapter{

    private static final int TILE_SIZE = 32;

    private final OrthographicCamera camera;
    private final Viewport viewport; 
    private final BuildManager buildManager;

    private final Vector3 mouseWorldPos = new Vector3();

    //saves hovering Tiles
    private int hoverX = -1;
    private int hoverY = -1;
    

    public InputEngine(OrthographicCamera camera, Viewport viewport, BuildManager buildManager) {
        this.camera = camera;
        this.viewport = viewport;

        this.buildManager = buildManager;
    }

    public void handleInput() {

        float cameraSpeed = 750f;
        float deltaTime = Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.camera.position.y += cameraSpeed * deltaTime;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.camera.position.y -= cameraSpeed * deltaTime;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.camera.position.x += cameraSpeed * deltaTime;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.camera.position.x -= cameraSpeed * deltaTime;
        }
        
        camera.update();

        mouseWorldPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(mouseWorldPos);

        hoverX = (int) Math.floor(mouseWorldPos.x / TILE_SIZE);
        hoverY = (int) Math.floor(mouseWorldPos.y / TILE_SIZE);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            buildManager.interactAt(hoverX, hoverY);
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // places selected tile dragging
        buildManager.interactAt(hoverX, hoverY);
        return true;
    }
    
    @Override
    public boolean scrolled(float amountX, float amountY) {

        float zoomSpeed = 0.1f;

        camera.zoom += amountY * zoomSpeed;
        camera.zoom = MathUtils.clamp(camera.zoom, 0.4f, 14.0f);
        camera.update();

        return true;

    }

    public int getHoverX() { return hoverX; }
    public int getHoverY() { return hoverY; }
}