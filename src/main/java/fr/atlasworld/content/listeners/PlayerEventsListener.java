package fr.atlasworld.content.listeners;

import fr.atlasworld.content.api.block.CustomBlock;
import fr.atlasworld.content.registering.Registry;
import fr.atlasworld.content.world.WorldUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class PlayerEventsListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().isRightClick() && event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.NOTE_BLOCK){
            event.setCancelled(true);
        }

        if (event.getItem() != null) {
            Registry.ITEM.getEntries().forEach(item -> {
                if (item.isSame(event.getItem())) {
                    item.onItemUse(event.getPlayer(), event.getClickedBlock(), event.getAction(), event.getBlockFace(), event.getInteractionPoint());
                }
            });
        }

        if (event.getClickedBlock() != null && CustomBlock.isCustomBlock(event.getClickedBlock())) {
            if (event.getPlayer().isSneaking()) {
                if (event.getAction().isRightClick()) {
                    Player player = event.getPlayer();
                    if (!player.getInventory().getItemInMainHand().getType().isBlock()) return;
                    WorldUtils.applyFaceMod(event.getClickedBlock().getLocation(), event.getBlockFace()).getBlock().setType(player.getInventory().getItemInMainHand().getType());
                }
            } else CustomBlock.getCustomBlock(event.getClickedBlock()).onPlayerInteract(event.getPlayer(), event.getPlayer().getWorld(), event.getClickedBlock().getLocation(), event.getBlockFace(), event.getAction());
        }
    }

    @EventHandler
    public void onPlayerHeld(PlayerItemHeldEvent event) {
        Registry.ITEM.getEntries().forEach(item -> {
            if (item.isSame(event.getPlayer().getInventory().getItemInMainHand())) {
                item.onItemHeld(event.getPlayer(), event.getNewSlot(), event.getPreviousSlot());
            }
        });
    }
}
