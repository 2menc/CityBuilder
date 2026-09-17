package com.github.cityBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.files.FileHandle;

import com.github.citybuilder.engine.services.AutoSaveService;
import com.github.citybuilder.model.map.TileType;
import com.github.citybuilder.model.map.WorldMap;
import com.github.citybuilder.utils.files.AutoSaver;
import com.github.citybuilder.utils.files.RuleLoader;

public class SaveLoad {

    private static final String SAVE_FILE_NAME = "TestSaveWorld";
    private static final String SAVE_FILE_PATH = "saves/" + SAVE_FILE_NAME + ".yaml";

    private WorldMap map;
    private AutoSaveService autoSaveService;

    @BeforeAll
    static void initEnvironment() {
        // Initialize LibGDX headless backend to enable Gdx.files API without rendering a window
        new HeadlessApplication(new ApplicationAdapter() {});
    }

    @BeforeEach
    void setup() {
        RuleLoader.loadRules();

        // Initialize a fresh map for each test
        int width = RuleLoader.RULES.getMapWidth();
        int height = RuleLoader.RULES.getMapHeight();
        this.map = new WorldMap(width, height);
        
        // Initialize the save service
        this.autoSaveService = new AutoSaveService(RuleLoader.RULES, this.map, SAVE_FILE_NAME);
    }


    void tearDown() {
        // Clean up the generated save file after each test to ensure test isolation
        FileHandle saveFile = Gdx.files.local(SAVE_FILE_PATH);
        if (saveFile.exists()) {
            saveFile.delete();
        }
    }

    @Test 
    void testSaveFileCreationAndContent() {
        // Arrange: Place a specific tile on the map
        map.setTileType(5, 5, TileType.ZONE_RESIDENTIAL);

        // Act: Trigger the save process
        autoSaveService.saveGame();

        // Assert: Verify the file exists on the disk
        FileHandle saveFile = Gdx.files.local(SAVE_FILE_PATH);
        assertTrue(saveFile.exists(), "The save file should be created on the disk.");

        // Assert: Verify the file actually contains data
        String fileContent = saveFile.readString();
        assertTrue(fileContent.length() > 0, "The save file should not be empty.");
    }    


    @Test 
    void testLoadRestoresMapState() {
        // Arrange: Place a road, save the game, then alter the map to simulate a dirty state
        map.setTileType(10, 10, TileType.ROAD);
        autoSaveService.saveGame();
        
        map.setTileType(10, 10, TileType.GRASS); 

        // Act: Load the previously saved game
        AutoSaver.loadGame(SAVE_FILE_NAME);

        // Assert: Verify the map state has been successfully restored
        TileType restoredTile = map.getTileType(10, 10);
        assertEquals(TileType.ROAD, restoredTile, "Loading the game should restore the ROAD tile at (10, 10).");
    }
}