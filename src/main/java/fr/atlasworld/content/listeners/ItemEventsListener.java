package fr.atlasworld.content.listeners;

import fr.atlasworld.content.registering.Registry;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Objects;
import java.util.UUID;

public class ItemEventsListener implements Listener {
    @EventHandler
    public void onItemUseEvent(PlayerInteractEvent event) {
        if (event.getAction().isRightClick() && event.getClickedBlock() != null && event.getClickedBlock().getType().equals(Material.NOTE_BLOCK)){
            event.setCancelled(true);
        }

        if (event.getItem() != null) {
            Registry.ITEM.getEntries().forEach(item -> {
                if (item.isSame(event.getItem())) {
                    item.onUse(event.getPlayer(), event.getClickedBlock(), event.getAction(), event.getBlockFace(), event.getInteractionPoint());
                }
            });
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            Registry.ITEM.getEntries().forEach(item -> {
                if (item.isSame(player.getInventory().getItemInMainHand())) {
                    item.onAttack(player, event.getEntity(), event.getDamage());
                }
            });
        }
    }
}
