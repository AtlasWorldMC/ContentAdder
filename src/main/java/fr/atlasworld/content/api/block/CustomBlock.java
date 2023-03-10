package fr.atlasworld.content.api.block;

import de.tr7zw.nbtapi.NBTBlock;
import fr.atlasworld.content.api.utils.Identifier;
import fr.atlasworld.content.api.utils.MaterialColor;
import fr.atlasworld.content.api.sound.SoundGroup;
import fr.atlasworld.content.nbt.BlockNbtKeys;
import fr.atlasworld.content.registering.Registry;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class CustomBlock {
    protected final BlockProperties properties;
    protected Component displayName;

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

    public boolean isPushable() {
        return properties.isPushable();
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

    public void onPlaced(Block block, World world, Location location) {}
    public void onPlayerInteract(Player player, World world, Location location) {}
    public void onBlockDestroyedByPlayer(Player player, World world, Location oldLocation) {}
    public void onBlockExploded(World world, Location oldLocation) {}


    public static Block removeCustomBlockByPlayer(Block block, Player player) {
        //Fire event
        Objects.requireNonNull(getCustomBlock(block)).onBlockDestroyedByPlayer(player, block.getWorld(), block.getLocation());

        return removeCustomBlock(block);
    }

    public static Block removeCustomBlockByExplosion(Block block) {
        //Fire event
        Objects.requireNonNull(getCustomBlock(block)).onBlockExploded(block.getWorld(), block.getLocation());

        return removeCustomBlock(block);
    }

    public static Block removeCustomBlock(Block block) {
        //Remove Stored NBT Data
        new NBTBlock(block).getData().setString(BlockNbtKeys.blockIdentifierKey, "");
        block.setType(Material.AIR);

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

        //Fire Event
        cBlock.onPlaced(block, block.getWorld(), block.getLocation());

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
