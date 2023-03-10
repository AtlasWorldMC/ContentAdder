package fr.atlasworld.content.listeners;

import fr.atlasworld.content.registering.Registry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemEventsListener implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            Registry.ITEM.getEntries().forEach(item -> {
                if (item.isSame(player.getInventory().getItemInMainHand())) {
                    item.onHurtEntity(player, event.getEntity(), event.getDamage());
                }
            });
        }
    }

    @EventHandler
    public void onItemDropped(PlayerDropItemEvent event) {
        Registry.ITEM.getEntries().forEach(item -> {
            if (item.isSame(event.getItemDrop().getItemStack())) {
                item.onItemDropped(event.getPlayer(), event.getItemDrop());
            }
        });
    }
}
