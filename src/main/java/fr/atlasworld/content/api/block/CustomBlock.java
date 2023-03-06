package fr.atlasworld.content.api.block;

import de.tr7zw.nbtapi.NBTBlock;
import fr.atlasworld.content.api.Identifier;
import fr.atlasworld.content.api.block.material.MaterialColor;
import fr.atlasworld.content.api.sound.SoundGroup;
import fr.atlasworld.content.nbt.BlockNbtKeys;
import fr.atlasworld.content.registering.Registry;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class CustomBlock {
    private final BlockProperties properties;
    private Component displayName;

    public CustomBlock(BlockProperties properties) {
        this.properties = properties;
    }

    public CustomBlock setDisplayName(Component displayName) {
        this.displayName = displayName;
        return this;
    }

    public Component getDisplayName() {
        return displayName;
    }

    public List<Material> getReplaceableBlocks() {
        return properties.getReplaceableBlocks();
    }

    public MaterialColor getMaterialColor() {
        return properties.getMaterialColor();
    }

    public float getExplosionResistance() {
        return properties.getExplosionResistance();
    }

    public float getDestroyTime() {
        return properties.getDestroyTime();
    }

    public boolean isRequireCorrectToolForDrops() {
        return properties.isRequireCorrectToolForDrops();
    }

    public SoundGroup.Block getBlockSounds() {
        return properties.getBlockSounds();
    }

    public List<Component> appendLore(List<Component> loreList) {
        return loreList;
    }

    public @Nullable ItemStack getAsItem() {
        if (Registry.ITEM.containsIdentifier(Registry.BLOCK.getIdentifier(this))){
            return Registry.ITEM.getEntry(Registry.BLOCK.getIdentifier(this)).getItem();
        }
        return null;
    }

    public Block placeBlock(Block block) {
        return placeBlock(block, this);
    }

    public void onPlaced(Player player, World world, Location location) {}
    public void onRightClicked(Player player, World world, Location location, ItemStack usedItem) {}
    public void onHit(Player player, World world, Location location, ItemStack usedItem) {}
    public void onBreak(Player player, World world, Location oldLocation, ItemStack usedItem) {}

    public static Block replaceBlock(Block block, Material type) {
        new NBTBlock(block).getData().setString(BlockNbtKeys.blockIdentifierKey, "");

        //Remove Related data
        block.setType(type);

        return block;
    }

    public static Block placeBlock(Block block, CustomBlock cBlock) {
        Identifier cBlockId = Registry.BLOCK.getIdentifier(cBlock);
        if (cBlockId == null) throw new IllegalArgumentException("Unable to get CustomBlock Identifier!");

        //Block Data
        CustomBlockState blockState = Registry.BLOCK.getBlockState(cBlockId);
        block.setType(Material.NOTE_BLOCK, false);
        NoteBlock placedBlockState = (NoteBlock) block.getBlockData();
        placedBlockState.setInstrument(CustomBlockState.getInstrument(blockState.getInstrument()));
        placedBlockState.setNote(new Note(blockState.getNote()));
        placedBlockState.setPowered(blockState.isPowered());
        block.setBlockData(placedBlockState, false);

        new NBTBlock(block).getData().setString(BlockNbtKeys.blockIdentifierKey, cBlockId.toString());

        //Effects
        block.getWorld().playSound(block.getLocation(), cBlock.getBlockSounds().getPlaceSound(), 1F, 0.8F);

        return block;
    }

    public static boolean isCustomBlock(Block block) {
        return !new NBTBlock(block).getData().getString(BlockNbtKeys.blockIdentifierKey).isEmpty();
    }

    public static @Nullable CustomBlock getCustomBlock(Block block) {
        return Registry.BLOCK.getEntry(new Identifier(new NBTBlock(block).getData().getString(BlockNbtKeys.blockIdentifierKey)));
    }

    public static @Nullable Identifier getBlockIdentifier(Block block) {
        return Registry.BLOCK.getIdentifier(getCustomBlock(block));
    }
}
