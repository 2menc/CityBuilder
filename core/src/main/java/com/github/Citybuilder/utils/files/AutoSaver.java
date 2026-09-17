package com.github.citybuilder.utils.files;

import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.representer.Representer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.github.citybuilder.model.map.WorldMap;
import com.github.citybuilder.rules.GameRules;

public class AutoSaver {

    public static void saveGame(GameRules rules, WorldMap map, String worldName) {

        FileHandle fileHandle = Gdx.files.local("saves/" + worldName + ".yaml");
        Yaml yaml = createConfiguredYaml();

        GameMap mapForSaving = new GameMap(map); 

        SaveState stateToSave = new SaveState(rules, mapForSaving, worldName);

        try (Writer writer = fileHandle.writer(false)) {
            yaml.dump(stateToSave, writer);
            Gdx.app.log("AutoSaver", "game saved in: " + fileHandle.path());
        } catch (Exception e) {
            Gdx.app.error("AutoSaver", "game saving error", e);
            
        }
    }   



    public static SaveState loadGame(String saveFileName) {
        FileHandle fileHandle = Gdx.files.local("saves/" + saveFileName + ".yaml");
        
        if (!fileHandle.exists()) {
            Gdx.app.error("AutoSaver", "saving file does not exist: " + saveFileName);
            return null;
        }

        Yaml yaml = createConfiguredYaml();

        try (Reader reader = fileHandle.reader()) {
            
            SaveState loadedState = yaml.loadAs(reader, SaveState.class);
            Gdx.app.log("AutoSaver", "game loaded");
            return loadedState;

        } catch (Exception e) {
            Gdx.app.error("AutoSaver", "game loading error", e);
            return null;
        }
    }

    private static Yaml createConfiguredYaml() {
        DumperOptions options = new DumperOptions();
        
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        
        options.setPrettyFlow(true); 

        Representer representer = new Representer(options);

        PropertyUtils customPropertyUtils = new PropertyUtils() {
            @Override
            protected Set<Property> createPropertySet(Class<?> type, BeanAccess bAccess) {

                // get default properties: default is alphabetical order
                Set<Property> defaultProperties = super.createPropertySet(type, bAccess);
                
                // only change SaveState class
                if (type.equals(SaveState.class)) {
                    List<Property> list = new ArrayList<>(defaultProperties);
                    Property mapProperty = null;
                    
                    // searches the map
                    for (Property p : list) {
                        if (p.getName().equals("map")) {
                            mapProperty = p;
                            break;
                        }
                    }
                    
                    // it founds the map, places it at the end
                    if (mapProperty != null) {
                        list.remove(mapProperty);
                        list.add(mapProperty);
                    }
                    
                    return new LinkedHashSet<>(list);
                }
                
                return defaultProperties;
            }
        };

        customPropertyUtils.setBeanAccess(BeanAccess.FIELD); 
        
        representer.setPropertyUtils(customPropertyUtils);
        
        return new Yaml(representer, options);
    }

}
