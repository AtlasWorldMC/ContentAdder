package fr.atlasworld.content.listeners;

import fr.atlasworld.content.datagen.AssetsManager;
import fr.atlasworld.content.events.registration.RegisterBlockEvent;
import fr.atlasworld.content.events.registration.RegisterItemEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

public class ServerEventsListener implements Listener {
    @EventHandler
    public void onServerStart(ServerLoadEvent event) {
        Bukkit.getPluginManager().callEvent(new RegisterItemEvent());
        Bukkit.getPluginManager().callEvent(new RegisterBlockEvent());
        AssetsManager.modifyItemModelFiles();
    }
}
