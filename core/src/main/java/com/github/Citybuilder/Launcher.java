package com.github.Citybuilder;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.Citybuilder.map.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Launcher extends ApplicationAdapter {

    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private WorldMap map;
    private MapRenderer mapRenderer;
    private Viewport viewPort;

    @Override
    public void create() {
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600); 

        this.viewPort = new FitViewport(800, 600);

        
        this.shapeRenderer = new ShapeRenderer();
        this.map = new WorldMap(50, 50);

        //DEBUG
        map.setTileType(10, 10, TileType.ROAD);

        this.mapRenderer = new MapRenderer(map);

    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        camera.update();

        mapRenderer.render(camera, shapeRenderer);
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
