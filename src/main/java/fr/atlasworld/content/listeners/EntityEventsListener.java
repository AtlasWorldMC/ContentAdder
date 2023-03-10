package fr.atlasworld.content.listeners;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import fr.atlasworld.content.registering.Registry;
import io.papermc.paper.event.entity.EntityMoveEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityEventsListener implements Listener {
    @EventHandler
    public void onEntityRemovedFromWorld(EntityRemoveFromWorldEvent event) {
        if (event.getEntityType().equals(EntityType.DROPPED_ITEM)) {
            Item droppedItem = (Item) event.getEntity();

            Registry.ITEM.getEntries().forEach(item -> {
                if (item.isSame(droppedItem.getItemStack())) {
                    item.onItemDestroyed(droppedItem);
                }
            });
        }
    }
}
