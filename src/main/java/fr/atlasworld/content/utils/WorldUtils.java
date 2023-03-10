package fr.atlasworld.content.utils;

import fr.atlasworld.content.api.block.CustomBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.stream.Collectors;

public class WorldUtils {
    public static void placeCustomBlock(Player player, Block clickedBlock, BlockFace face, CustomBlock block, boolean force, boolean tickBlocks) {
        Location newBlockLocation = clickedBlock.getLocation();
        if (!block.getReplaceableBlocks().contains(clickedBlock.getType())){
            getLocationDirection(newBlockLocation, face);
        }

        if (!force) {
            if (!block.getReplaceableBlocks().contains(newBlockLocation.getBlock().getType()) || new Location(newBlockLocation.getWorld(), newBlockLocation.getBlockX() + 0.5, newBlockLocation.getY() + 0.5, newBlockLocation.getBlockZ() + 0.5).getNearbyEntities(0.5, 0.5, 0.5).stream().filter(entity -> entity.getType() != EntityType.DROPPED_ITEM).toList().size() > 0) {
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
            CustomBlock.removeCustomBlock(newBlockLocation.getBlock());
        }
    }

    public static Location getLocationDirection(Location loc, BlockFace face) {
        switch (face) {
            case UP -> loc.setY(loc.getY() + 1);
            case DOWN -> loc.setY(loc.getY() - 1);
            case SOUTH -> loc.setZ(loc.getBlockZ() + 1);
            case NORTH -> loc.setZ(loc.getBlockZ() - 1);
            case EAST -> loc.setX(loc.getBlockX() + 1);
            case WEST -> loc.setX(loc.getBlockX() - 1);
        }
        return loc;
    }
}
