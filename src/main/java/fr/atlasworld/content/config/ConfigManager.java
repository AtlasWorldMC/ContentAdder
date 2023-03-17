package fr.atlasworld.content.config;

import fr.atlasworld.content.ContentAdder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private static File file;
    private static FileConfiguration configFile;

    public static void setup(){
        file = new File(ContentAdder.plugin.getDataFolder(), "ContentAdder.yml");

        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException ignored){

            }
        }
        configFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get(){
        return configFile;
    }

    public static void save(){
        try{
            configFile.save(file);
        }catch (IOException e){
            System.out.println("Couldn't save file");
        }
    }

    public static void reload(){
        configFile = YamlConfiguration.loadConfiguration(file);
    }
}

