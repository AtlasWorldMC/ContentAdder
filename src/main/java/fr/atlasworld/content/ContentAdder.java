package fr.atlasworld.content;

import fr.atlasworld.content.datagen.AssetsManager;
import fr.atlasworld.content.events.registration.RegisterBlockEvent;
import fr.atlasworld.content.events.registration.RegisterItemEvent;
import fr.atlasworld.content.listeners.*;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import java.io.File;

public final class ContentAdder extends JavaPlugin {
    public static Logger logger;
    public static Plugin plugin;
    public static final String namespace = "content_adder";
    public static final Component prefix = Component.text(ChatColor.RED + "[" + ChatColor.GOLD + "ContentAdder" + ChatColor.RED + "] ");
    public static  File pluginFile;
    @Override
    public void onEnable() {
        logger = getSLF4JLogger();
        plugin = this;
        pluginFile = getFile();
        registerListeners(new ItemEventsListener(), new BlockEventsListener(), new PlayerEventsListener(), new EntityEventsListener(), new ServerEventsListener());

        //Gen Texturepack
        AssetsManager.generateTexturePack();
    }

    @Override
    public void onDisable() {

    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) Bukkit.getPluginManager().registerEvents(listener, this);
    }

}
