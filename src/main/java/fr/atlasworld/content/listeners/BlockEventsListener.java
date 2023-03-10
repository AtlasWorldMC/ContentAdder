package fr.atlasworld.content.listeners;

import de.tr7zw.nbtapi.NBTBlock;
import fr.atlasworld.content.ContentAdder;
import fr.atlasworld.content.api.block.CustomBlock;
import fr.atlasworld.content.nbt.BlockNbtKeys;
import fr.atlasworld.content.registering.Registry;
import fr.atlasworld.content.utils.WorldUtils;
import io.papermc.paper.event.entity.EntityMoveEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;

import java.util.Objects;

public class BlockEventsListener implements Listener {
    @EventHandler
    public void onPlayNote(NotePlayEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event) {
        if (event.getBlock().getType().equals(Material.NOTE_BLOCK) && !CustomBlock.isCustomBlock(event.getBlock())) {
            event.getPlayer().sendMessage(ContentAdder.prefix.append(Component.translatable("chat." + ContentAdder.namespace + ".can_not_place_note_block").color(TextColor.color(192, 0, 0))));
            event.setCancelled(true);
        }
    }



    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType().equals(Material.NOTE_BLOCK) && CustomBlock.isCustomBlock(event.getBlock())) {
            event.setCancelled(true);
            CustomBlock customBlock = CustomBlock.getCustomBlock(event.getBlock());
            CustomBlock.removeCustomBlockByPlayer(event.getBlock(), event.getPlayer());

            if (customBlock.getAsItem() != null && event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), customBlock.getAsItem());
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockExploded(BlockExplodeEvent event) {
        if (event.getBlock().getType().equals(Material.NOTE_BLOCK) && CustomBlock.isCustomBlock(event.getBlock())) {
            event.setCancelled(true);
            CustomBlock customBlock = Objects.requireNonNull(CustomBlock.getCustomBlock(event.getBlock()));
            CustomBlock.removeCustomBlockByExplosion(event.getBlock());

            if (customBlock.getAsItem() != null) {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), customBlock.getAsItem());
            }
        }
    }

    @EventHandler
    public void onBlockPistonExtendEvent(BlockPistonExtendEvent event) {
        event.getBlocks().forEach(block -> {
            if (block.getType().equals(Material.NOTE_BLOCK) && CustomBlock.isCustomBlock(block)) {
                if (CustomBlock.getCustomBlock(block).isPushable()) {
                    new NBTBlock(WorldUtils.getLocationDirection(block.getLocation(), event.getDirection()).getBlock()).getData().setString(BlockNbtKeys.blockIdentifierKey, CustomBlock.getBlockIdentifier(block).toString());
                    new NBTBlock(block).getData().setString(BlockNbtKeys.blockIdentifierKey, "");
                    return;
                }
                event.setCancelled(true);
            }
        });
    }

    @EventHandler
    public void onBlockPistonRetractEvent(BlockPistonRetractEvent event) {
        event.getBlocks().forEach(block -> {
            if (block.getType().equals(Material.NOTE_BLOCK) && CustomBlock.isCustomBlock(block)) {
                if (CustomBlock.getCustomBlock(block).isPushable()) {
                    new NBTBlock(WorldUtils.getLocationDirection(block.getLocation(), event.getDirection()).getBlock()).getData().setString(BlockNbtKeys.blockIdentifierKey, CustomBlock.getBlockIdentifier(block).toString());
                    new NBTBlock(block).getData().setString(BlockNbtKeys.blockIdentifierKey, "");
                    return;
                }
                event.setCancelled(true);
            }
        });
    }
}
