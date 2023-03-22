package fr.atlasworld.content.api.block;

import de.tr7zw.nbtapi.NBTBlock;
import fr.atlasworld.content.api.Identifier;
import fr.atlasworld.content.api.sound.SoundGroup;
import fr.atlasworld.content.nbt.ContentNbtKeys;
import fr.atlasworld.content.registering.Registry;
import fr.atlasworld.content.world.WorldUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

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

    public void onPlaced(Block block, World world, Location location) {}
    public void onPlayerInteract(Player player, World world, Location location, BlockFace face, Action action) {
        if (action.isLeftClick()) return;
        if (!player.getInventory().getItemInMainHand().getType().isBlock()) return;
        WorldUtils.applyFaceMod(location, face).getBlock().setType(player.getInventory().getItemInMainHand().getType());
    }
    public void onBlockDestroyedByPlayer(Player player, World world, Location oldLocation) {}
    public void onBlockExploded(World world, Location oldLocation) {}

    public static void removeCustomBlock(Block block, Material replace) {
        if (!isCustomBlock(block)) {
            throw new IllegalStateException("Given Block isn't a custom block!");
        }

        //Remove Stored NBT Data
        new NBTBlock(block).getData().setString(ContentNbtKeys.Block.blockIdentifierKey, "");
        block.setType(replace);
    }

    public void place(Block block) {
        new NBTBlock(block).getData().setString(ContentNbtKeys.Block.blockIdentifierKey, getIdentifier().toString());
        block.setType(Material.NOTE_BLOCK, true);

        //Block State
        CustomBlockState state = Registry.BLOCK.getBlockState(getIdentifier());
        NoteBlock blockData = (NoteBlock) block.getBlockData();
        blockData.setInstrument(CustomBlockState.getInstrument(state.getInstrument()));
        blockData.setNote(new Note(state.getNote()));
        blockData.setPowered(state.isPowered());
        block.setBlockData(blockData, false);
    }

    public Identifier getIdentifier() {
        return Registry.BLOCK.getIdentifier(this);
    }

    public static boolean isCustomBlock(Block block) {
        return !new NBTBlock(block).getData().getString(ContentNbtKeys.Block.blockIdentifierKey).isEmpty();
    }

    public static @Nullable CustomBlock getCustomBlock(Block block) {
        return Registry.BLOCK.getEntry(new Identifier(new NBTBlock(block).getData().getString(ContentNbtKeys.Block.blockIdentifierKey)));
    }

    public static @Nullable Identifier getBlockIdentifier(Block block) {
        return Registry.BLOCK.getIdentifier(getCustomBlock(block));
    }
}
