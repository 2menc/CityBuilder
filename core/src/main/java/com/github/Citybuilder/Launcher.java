package com.github.citybuilder;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.citybuilder.model.map.*;
import com.github.citybuilder.view.*;
import com.github.citybuilder.view.hud.HUDoverlay;
import com.github.citybuilder.engine.*;

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
    private BuildManager buildManager;
    private HUDoverlay hudOverlay;
    private InputMultiplexer multiplexer;

    public Launcher() {}
    
    @Override
    public void create() {
        this.camera = new OrthographicCamera();
        this.viewPort = new FillViewport(CAMERA_WIDTH, CAMERA_HEIGHT, camera);

        this.shapeRenderer = new ShapeRenderer();

        this.map = new WorldMap(WORLD_WIDTH, WORLD_HEIGHT);

        this.buildManager = new BuildManager(map);

        this.mapRenderer = new MapRenderer(map);
        this.inputEngine = new InputEngine(camera, viewPort, buildManager);
        Gdx.input.setInputProcessor(inputEngine);

        this.hudOverlay = new HUDoverlay(buildManager);
        this.multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(hudOverlay.getStage()  );
        multiplexer.addProcessor(inputEngine);

        Gdx.input.setInputProcessor(multiplexer);    
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        inputEngine.handleInput();

        viewPort.apply();

        mapRenderer.render(camera, shapeRenderer, inputEngine.getHoverX(), inputEngine.getHoverY());
        hudOverlay.render();
    }

    @Override
    public void resize(int width, int height) {
        this.viewPort.update(width, height, true); 
        this.hudOverlay.resize(width, height); // updates toolbar position
    }
    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    public static void main(String[] args) {
        
    }
}
