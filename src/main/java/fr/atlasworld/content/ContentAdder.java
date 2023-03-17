package fr.atlasworld.content;

import fr.atlasworld.content.config.ConfigManager;
import fr.atlasworld.content.datagen.AssetsManager;
import fr.atlasworld.content.listeners.*;
import fr.atlasworld.content.web.WebUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;


public final class ContentAdder extends JavaPlugin {
    public static Logger logger;
    public static Plugin plugin;
    public static final String namespace = "content_adder";
    public static final Component prefix = Component.text(ChatColor.RED + "[" + ChatColor.GOLD + "ContentAdder" + ChatColor.RED + "] ");
    @Override
    public void onEnable() {
        logger = getSLF4JLogger();
        plugin = this;
        registerListeners(new ItemEventsListener(), new BlockEventsListener(), new PlayerEventsListener(), new EntityEventsListener(), new ServerEventsListener());

        //Config Stuff

        ConfigManager.setup();
        ConfigManager.get().addDefault("DevEnv", false);
        ConfigManager.get().addDefault("EnableWebServer", true);
        ConfigManager.get().addDefault("WebServerIP", WebUtils.getIp());
        ConfigManager.get().addDefault("WebServerPort", 25585);
        ConfigManager.get().addDefault("GenerateTexturePack", true);
        ConfigManager.get().addDefault("TexturePackDescription", "ContentAdder assets!");
        ConfigManager.get().options().copyDefaults(true);
        ConfigManager.save();

        //Gen TexturePack
        if (ConfigManager.get().getBoolean("GenerateTexturePack")) {
            AssetsManager.prepareTexturePack();
        }
    }

    @Override
    public void onDisable() {

    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) Bukkit.getPluginManager().registerEvents(listener, this);
    }

}
