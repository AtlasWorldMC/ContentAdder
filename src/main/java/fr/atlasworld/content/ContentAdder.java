package fr.atlasworld.content;

import com.comphenix.protocol.ProtocolLibrary;
import fr.atlasworld.content.listeners.BlockEventsListener;
import fr.atlasworld.content.listeners.EntityEventsListener;
import fr.atlasworld.content.listeners.ItemEventsListener;
import fr.atlasworld.content.listeners.PlayerEventsListener;
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
        registerListeners(new ItemEventsListener(), new BlockEventsListener(), new PlayerEventsListener(), new EntityEventsListener());
    }

    @Override
    public void onDisable() {

    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) Bukkit.getPluginManager().registerEvents(listener, this);
    }
}
