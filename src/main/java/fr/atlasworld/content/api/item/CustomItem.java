package fr.atlasworld.content.api.item;

import fr.atlasworld.content.registering.Registry;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomItem {
    protected final Material parent;
    protected final ItemProperties properties;
    protected final int customModelData;
    protected Component displayName;
    public CustomItem(Material parent, ItemProperties properties) {
        this.parent = parent;
        this.properties = properties;
        this.customModelData = Registry.ITEM.getCustomModelData(parent);
    }

    public Material getParent() {
        return parent;
    }
    public int getCustomModelData() {
        return customModelData;
    }

    public Component getDisplayName() {
        return displayName;
    }

    public CustomItem setDisplayName(Component displayName) {
        this.displayName = displayName;
        return this;
    }

    public List<Component> appendLore(List<Component> loreList) {
        return loreList;
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(parent);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(customModelData);
        meta.displayName(displayName);
        meta.lore(appendLore(new ArrayList<>()));
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack getParentItem() {
        return new ItemStack(parent);
    }

    public boolean isSame(ItemStack item) {
        return item.getType() == parent && item.getItemMeta().getCustomModelData() == customModelData;
    }

    public boolean isFireResistant() {
        return properties.isFireResistant();
    }

    public int getMaxStackSize() {
        return properties.getMaxStackSize();
    }

    public void onItemUse(Player player, Block usedBlock, Action action, BlockFace face, Location location) {}
    public void onHurtEntity(Player player, Entity target, double damage) {}
    public void onItemDropped(Player player, Item droppedItem) {}
    public void onItemDestroyed(Item itemEntity) {}
    public void onItemHeld(Player player, int currentSlot, int previousSlot) {}

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CustomItem cItem) {
            return cItem.getParent() == this.getParent() && cItem.getCustomModelData() == this.getCustomModelData();
        }
        if (obj instanceof ItemStack vItem) {
            return isSame(vItem);
        }
        return false;
    }
}
