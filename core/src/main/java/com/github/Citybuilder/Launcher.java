package com.github.Citybuilder;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.Citybuilder.model.map.*;
import com.github.Citybuilder.view.*;
import com.github.Citybuilder.engine.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Launcher extends ApplicationAdapter {

    private final static long WORLD_WIDTH = 500;
    private final static long WORLD_HEIGHT = 500;

    private final static long CAMERA_WIDTH = 800;
    private final static long CAMERA_HEIGHT = 600;

    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private WorldMap map;
    private MapRenderer mapRenderer;
    private Viewport viewPort;
    private InputEngine inputEngine; 

    @Override
    public void create() {
        this.camera = new OrthographicCamera();
        this.viewPort = new FillViewport(CAMERA_WIDTH, CAMERA_HEIGHT, camera);

        this.shapeRenderer = new ShapeRenderer();

        this.map = new WorldMap(WORLD_WIDTH, WORLD_HEIGHT);

        this.mapRenderer = new MapRenderer(map);
        this.inputEngine = new InputEngine(camera, viewPort);
        Gdx.input.setInputProcessor(inputEngine);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        inputEngine.handleInput();

        viewPort.apply();

        mapRenderer.render(camera, shapeRenderer, inputEngine.getHoverX(), inputEngine.getHoverY());
    }

    @Override
    public void resize(int width, int height) {
        this.viewPort.update(width, height, true); 
}
    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    public static void main(String[] args) {
        
    }
}
