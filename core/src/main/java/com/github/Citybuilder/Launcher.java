package com.github.citybuilder;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.citybuilder.model.map.*;
import com.github.citybuilder.utils.RuleLoader;
import com.github.citybuilder.view.*;
import com.github.citybuilder.view.hud.HUDButtonsColors;
import com.github.citybuilder.view.hud.HUDoverlay;
import com.github.citybuilder.engine.*;
import com.github.citybuilder.engine.services.FinancialService;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Launcher extends ApplicationAdapter {

    private final static long CAMERA_WIDTH = 800;
    private final static long CAMERA_HEIGHT = 600;

    private final static long TILE_SIZE = 32;

    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private WorldMap map;
    private MapRenderer mapRenderer;
    private Viewport viewPort;
    private InputEngine inputEngine; 
    private BuildManager buildManager;
    private HUDoverlay hudOverlay;
    private InputMultiplexer multiplexer;
    private UpdateEngine updateEngine;
    private  FinancialService financialService;

    public Launcher() {}
    
    @Override
    public void create() {

        // rules loading
        RuleLoader.loadRules();

        // map
        this.map = new WorldMap(RuleLoader.RULES.getMapWidth(), RuleLoader.RULES.getMapHeight());

        // finance
        this.financialService = new FinancialService(RuleLoader.RULES.getStartingBalance());

        // building system
        this.buildManager = new BuildManager(map, financialService);

        // hud
        this.hudOverlay = new HUDoverlay(buildManager);
        this.initializeButtonListeners();

        // updater
        this.updateEngine = new UpdateEngine(this.hudOverlay);

        // tickables
        this.updateEngine.register(financialService);

        // renderer
        this.shapeRenderer = new ShapeRenderer();
        this.mapRenderer = new MapRenderer(map);

        // camera
        this.camera = new OrthographicCamera();
        this.viewPort = new FillViewport(CAMERA_WIDTH, CAMERA_HEIGHT, camera);

        centerCameraOnMap();

        // input
        this.multiplexer = new InputMultiplexer();
        this.inputEngine = new InputEngine(camera, viewPort, buildManager);
        Gdx.input.setInputProcessor(inputEngine);

        multiplexer.addProcessor(hudOverlay.getStage());
        multiplexer.addProcessor(inputEngine);

        Gdx.input.setInputProcessor(multiplexer);  
    }

    @Override
    public void render() {

        float deltaTime = Gdx.graphics.getDeltaTime();
        this.updateEngine.update(deltaTime);

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        inputEngine.handleInput();

        hudOverlay.updateHUD(financialService.getBalance(), financialService.getIncomePerCycle(), 
            financialService.getExpensesPerCycle());

        viewPort.apply();

        mapRenderer.render(camera, shapeRenderer, inputEngine.getHoverX(), inputEngine.getHoverY(), financialService, buildManager);
        hudOverlay.render();
    }

    @Override
    public void resize(int width, int height) {
        this.viewPort.update(width, height, false); 

        camera.update();

        if (hudOverlay != null) {
            hudOverlay.resize(width, height);
        }
    }
    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    private void centerCameraOnMap() {
        float mapWidthPixels = RuleLoader.RULES.getMapWidth() * TILE_SIZE;   
        float mapHeightPixels = RuleLoader.RULES.getMapHeight() * TILE_SIZE; 

        camera.position.set(mapWidthPixels / 2f, mapHeightPixels / 2f, 0);
        camera.update();
    }

    private void initializeButtonListeners() {

        // pause
        hudOverlay.ifRequestedToTogglePauseGame(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                RuleLoader.RULES.setGameSpeed(0F);
                
                hudOverlay.toggleButtonIn(hudOverlay.getPauseButton(), HUDButtonsColors.GAME_SPEED);
                hudOverlay.toggleButtonOut(hudOverlay.getX1Button(), HUDButtonsColors.GAME_SPEED);
                hudOverlay.toggleButtonOut(hudOverlay.getX3Button(), HUDButtonsColors.GAME_SPEED);
                hudOverlay.toggleButtonOut(hudOverlay.getX5Button(), HUDButtonsColors.GAME_SPEED);
            }
        });

        // x1
        hudOverlay.ifRequestedTox1(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                RuleLoader.RULES.setGameSpeed(1F);

                hudOverlay.toggleButtonOut(hudOverlay.getPauseButton(), HUDButtonsColors.GAME_SPEED);
                hudOverlay.toggleButtonIn(hudOverlay.getX1Button(), HUDButtonsColors.GAME_SPEED);
                hudOverlay.toggleButtonOut(hudOverlay.getX3Button(), HUDButtonsColors.GAME_SPEED);
                hudOverlay.toggleButtonOut(hudOverlay.getX5Button(), HUDButtonsColors.GAME_SPEED);
            }
        });

        // x3
        hudOverlay.ifRequestedTox3(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                RuleLoader.RULES.setGameSpeed(3F);

                hudOverlay.toggleButtonOut(hudOverlay.getPauseButton(), HUDButtonsColors.GAME_SPEED);
                hudOverlay.toggleButtonOut(hudOverlay.getX1Button(), HUDButtonsColors.GAME_SPEED);
                hudOverlay.toggleButtonIn(hudOverlay.getX3Button(), HUDButtonsColors.GAME_SPEED);
                hudOverlay.toggleButtonOut(hudOverlay.getX5Button(), HUDButtonsColors.GAME_SPEED);
            }
        });

        // x5
        hudOverlay.ifRequestedTox5(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                RuleLoader.RULES.setGameSpeed(5F);

                hudOverlay.toggleButtonOut(hudOverlay.getPauseButton(), HUDButtonsColors.GAME_SPEED);
                hudOverlay.toggleButtonOut(hudOverlay.getX1Button(), HUDButtonsColors.GAME_SPEED);
                hudOverlay.toggleButtonOut(hudOverlay.getX3Button(), HUDButtonsColors.GAME_SPEED);
                hudOverlay.toggleButtonIn(hudOverlay.getX5Button(), HUDButtonsColors.GAME_SPEED);
            }
        });
        
        // bulldozer
        hudOverlay.ifRequestedBullDozer(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (buildManager.isBulldozerActive()) {

                    buildManager.setSelectedTileType(TileType.ROAD);
                    hudOverlay.toggleButtonOut(hudOverlay.getBullDozerButton(), HUDButtonsColors.BULLDOZER);
                } else {

                    buildManager.enableBulldozer();
                    hudOverlay.toggleButtonIn(hudOverlay.getBullDozerButton(), HUDButtonsColors.BULLDOZER);
                }
            }
        });
    }
}
