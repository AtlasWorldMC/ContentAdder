package fr.atlasworld.content.listeners;

import de.tr7zw.nbtapi.NBTBlock;
import fr.atlasworld.content.ContentAdder;
import fr.atlasworld.content.api.block.CustomBlock;
import fr.atlasworld.content.nbt.ContentNbtKeys;
import fr.atlasworld.content.world.WorldUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;

public class BlockEventsListener implements Listener {
    @EventHandler
    public void onPlayNote(NotePlayEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.NOTE_BLOCK && !CustomBlock.isCustomBlock(event.getBlock())) {
            event.getPlayer().sendMessage(ContentAdder.prefix.append(Component.translatable("chat." + ContentAdder.namespace + ".can_not_place_note_block").color(TextColor.color(192, 0, 0))));
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.NOTE_BLOCK && CustomBlock.isCustomBlock(event.getBlock())) {
            event.setCancelled(true);
            CustomBlock cBlock = CustomBlock.getCustomBlock(event.getBlock());

            CustomBlock.removeCustomBlock(event.getBlock(), Material.AIR);
            cBlock.onBlockDestroyedByPlayer(event.getPlayer(), event.getBlock().getWorld(), event.getBlock().getLocation());

            if (cBlock.getAsItem() != null && event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), cBlock.getAsItem());
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockExploded(BlockExplodeEvent event) {
        event.blockList().forEach(block -> {
            if (block.getType() == Material.NOTE_BLOCK && CustomBlock.isCustomBlock(block)) {
                event.setCancelled(true);

                CustomBlock cBlock = CustomBlock.getCustomBlock(block);
                CustomBlock.removeCustomBlock(block, Material.AIR);
                cBlock.onBlockExploded(block.getWorld(), block.getLocation());

                if (cBlock.getAsItem() != null) {
                    block.getWorld().dropItemNaturally(block.getLocation(), cBlock.getAsItem());
                }
            }
        });
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPistonExtendEvent(BlockPistonExtendEvent event) {
        event.getBlocks().forEach(block -> {
            if (block.getType() == Material.NOTE_BLOCK && CustomBlock.isCustomBlock(block) && !event.isCancelled()) {
                if (CustomBlock.getCustomBlock(block).isPushable()) {
                    new NBTBlock(WorldUtils.applyFaceMod(block.getLocation(), event.getDirection()).getBlock()).getData().setString(ContentNbtKeys.Block.blockIdentifierKey, CustomBlock.getBlockIdentifier(block).toString());
                    new NBTBlock(block).getData().setString(ContentNbtKeys.Block.blockIdentifierKey, "");
                    return;
                }
                event.setCancelled(true);
            }
        });
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPistonRetractEvent(BlockPistonRetractEvent event) {
        event.getBlocks().forEach(block -> {
            if (block.getType() == Material.NOTE_BLOCK && CustomBlock.isCustomBlock(block) && !event.isCancelled()) {
                if (CustomBlock.getCustomBlock(block).isPushable()) {
                    new NBTBlock(WorldUtils.applyFaceMod(block.getLocation(), event.getDirection()).getBlock()).getData().setString(ContentNbtKeys.Block.blockIdentifierKey, CustomBlock.getBlockIdentifier(block).toString());
                    new NBTBlock(block).getData().setString(ContentNbtKeys.Block.blockIdentifierKey, "");
                    return;
                }
                event.setCancelled(true);
            }
        });
    }
}
