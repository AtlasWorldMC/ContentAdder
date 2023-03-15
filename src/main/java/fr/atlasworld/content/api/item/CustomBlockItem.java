package fr.atlasworld.content.api.item;

import fr.atlasworld.content.ContentAdder;
import fr.atlasworld.content.api.block.CustomBlock;
import fr.atlasworld.content.world.WorldUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
public class CustomBlockItem extends CustomItem{
    protected final CustomBlock block;
    protected boolean canPlace;
    public CustomBlockItem(Material material, ItemProperties properties, CustomBlock block) {
        super(material, properties);
        this.block = block;
        this.canPlace = true;
    }

    @Override
    public void onItemUse(Player player, Block clickedBlock, Action action, BlockFace face, Location location) {
        if (clickedBlock == null) return;
        if (action.isRightClick()) {
            if (!canPlace) {
                return;
            }
            canPlace = false;
            Bukkit.getScheduler().runTaskLater(ContentAdder.plugin, () -> canPlace = true, 3);

            Location cBlockLocation = clickedBlock.getLocation();
            if (!block.getReplaceableBlocks().contains(clickedBlock.getType())) {
                WorldUtils.applyFaceMod(cBlockLocation, face);
            }

            if (!block.getReplaceableBlocks().contains(cBlockLocation.getBlock().getType()) ||
                    new Location(cBlockLocation.getWorld(), cBlockLocation.getX() + 0.5, cBlockLocation.getY() + 0.5, cBlockLocation.getZ() + 0.5)
                            .getNearbyEntities(0.5, 0.5, 0.5).stream().filter(entity -> entity.getType() != EntityType.DROPPED_ITEM).toList().size() > 0) {
                return;
            }

            Material oldBlockType = clickedBlock.getType();
            player.swingMainHand();
            block.place(cBlockLocation.getBlock());

            //Bukkit Event
            BlockPlaceEvent event = new BlockPlaceEvent(cBlockLocation.getBlock(), cBlockLocation.getBlock().getState(),
                    clickedBlock, player.getInventory().getItemInMainHand(), player, false, player.getHandRaised());
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                CustomBlock.removeCustomBlock(cBlockLocation.getBlock(), oldBlockType);
                return;
            }

            if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() -1);
            }

            //Fire CustomBlock event
            block.onPlaced(cBlockLocation.getBlock(), cBlockLocation.getWorld(), cBlockLocation);
        }
    }

    public Component getDisplayName() {
        return this.displayName;
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = super.getItem();
        ItemMeta meta = item.getItemMeta();
        meta.displayName(block.getDisplayName());
        meta.lore(block.appendLore(new ArrayList<>()));
        item.setItemMeta(meta);
        return item;
    }
}
