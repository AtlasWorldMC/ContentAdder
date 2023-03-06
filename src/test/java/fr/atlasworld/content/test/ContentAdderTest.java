package fr.atlasworld.content.test;

import fr.atlasworld.content.test.listeners.PlayerEvents;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

public class ContentAdderTest extends JavaPlugin {
    public static String NAMESPACE = "content_adder_test";
    public static Logger logger;

    @Override
    public void onEnable() {
        logger = getSLF4JLogger();
        registerListeners(new PlayerEvents());
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) Bukkit.getPluginManager().registerEvents(listener, this);
    }
}
