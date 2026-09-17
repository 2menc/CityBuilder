package com.github.citybuilder.utils.files;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import org.yaml.snakeyaml.Yaml;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.github.citybuilder.model.map.TileType;
import com.github.citybuilder.rules.GameRules;
import com.github.citybuilder.utils.Paths;

/**
 * gets rules from the rules file 
 */
public class RuleLoader {

    public static GameRules RULES;
    
    public static Map<TileType, Set<TileType>> getNotValidBuildMap() {
    
        FileHandle fileHandle = Gdx.files.internal(Paths.BUILDING_RULES.get());
        final Yaml ruleFile = new Yaml();

        Map<TileType, Set<TileType>> resultMap = new HashMap<>();

        try(final InputStream inputStream = fileHandle.read()) {

            Map<String, List<String>> raw = ruleFile.load(inputStream);

            if (raw != null) {
                for (Map.Entry<String, List<String>> entry: raw.entrySet()) {

                    TileType keyType = TileType.valueOf(entry.getKey().toUpperCase());

                    Set<TileType> invalidSet = entry.getValue().stream()
                            .map(name -> TileType.valueOf(name.toUpperCase()))
                            .collect(Collectors.toSet());

                    resultMap.put(keyType, invalidSet);
                } 
            }
        } catch (Exception e) {
            Gdx.app.error("RuleLoader", "Error loading construction rules YAML file", e);
        }

        return resultMap;   // non difensive copy
    }

    /**
     * init: loads all rules
     * @return
     */
    public static void loadRules() {

        final FileHandle fileHandle = Gdx.files.internal(Paths.GAME_RULES.get());
        final Yaml yaml = new Yaml();

        try(InputStream is = fileHandle.read()) {
            
            RULES = yaml.loadAs(is, GameRules.class);
        } catch (Exception e) {
            Gdx.app.error("RuleLoader", "rules loading error", e);
            RULES = new GameRules();
        }
    }

}