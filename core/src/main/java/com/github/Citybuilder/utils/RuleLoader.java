package com.github.citybuilder.utils;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import org.yaml.snakeyaml.Yaml;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.github.citybuilder.model.map.TileType;

/**
 * gets rules from the rules file 
 */
public class RuleLoader {
    
    public static Map<TileType, Set<TileType>> getNotValidBuildMap() {
    
        FileHandle fileHandle = Gdx.files.internal(Paths.GAMERULES_PATH.get());
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
}