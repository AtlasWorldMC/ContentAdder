package fr.atlasworld.content.api.item;

import de.tr7zw.nbtapi.NBTBlock;
import fr.atlasworld.content.ContentAdder;
import fr.atlasworld.content.api.block.CustomBlock;
import fr.atlasworld.content.nbt.BlockNbtKeys;
import fr.atlasworld.content.registering.Registry;
import fr.atlasworld.content.utils.WorldUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Objects;

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
            WorldUtils.placeCustomBlock(player, clickedBlock, face, block,false, true);
            if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() -1);
            }
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
