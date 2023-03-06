package fr.atlasworld.content.utils;

import fr.atlasworld.content.api.block.CustomBlock;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.List;

public class WorldUtils {
    public static void placeCustomBlock(Player player, Block clickedBlock, BlockFace face, CustomBlock block, boolean force) {
        Location newBlockLocation = clickedBlock.getLocation();
        if (!block.getReplaceableBlocks().contains(clickedBlock.getType())){
            switch (face) {
                case UP -> newBlockLocation.setY(newBlockLocation.getY() + 1);
                case DOWN -> newBlockLocation.setY(newBlockLocation.getY() - 1);
                case SOUTH -> newBlockLocation.setZ(newBlockLocation.getBlockZ() + 1);
                case NORTH -> newBlockLocation.setZ(newBlockLocation.getBlockZ() - 1);
                case EAST -> newBlockLocation.setX(newBlockLocation.getBlockX() + 1);
                case WEST -> newBlockLocation.setX(newBlockLocation.getBlockX() - 1);
            }
        }

        if (!force) {
            if (!block.getReplaceableBlocks().contains(newBlockLocation.getBlock().getType()) || new Location(newBlockLocation.getWorld(), newBlockLocation.getBlockX() + 0.5, newBlockLocation.getY() + 0.5, newBlockLocation.getBlockZ() + 0.5).getNearbyEntities(0.5, 0.5, 0.5).size() > 0) {
                return;
            }
        }

        player.swingMainHand();
        Material oldBlockType = newBlockLocation.getBlock().getType();
        block.placeBlock(newBlockLocation.getBlock());

        //Bukkit Event Stuff
        BlockPlaceEvent event = new BlockPlaceEvent(newBlockLocation.getBlock(), newBlockLocation.getBlock().getState(),
                clickedBlock, player.getInventory().getItemInMainHand(), player, true, player.getHandRaised());;
        Bukkit.getPluginManager().callEvent(event);
        if (!force && event.isCancelled()) {
            CustomBlock.replaceBlock(newBlockLocation.getBlock(), oldBlockType);
        }
    }
}
