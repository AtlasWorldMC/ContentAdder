package fr.atlasworld.content.api.block;

import fr.atlasworld.content.api.sound.SoundGroup;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockProperties {
    protected final SoundGroup.Block blockSounds;
    protected final MaterialColor color;
    protected float explosionResistance;
    protected float destroyTime;
    protected boolean requireCorrectToolForDrops;
    protected List<Material> replaceableBlocks;
    protected boolean pushable;

    public BlockProperties(MaterialColor color, SoundGroup.Block blockSounds) {
        this.color = color;
        this.blockSounds = blockSounds;
        this.requireCorrectToolForDrops = false;
        this.replaceableBlocks = new ArrayList<>();
        this.pushable = true;

        //Hardcoded Default ReplaceableBlocks
        this.replaceableBlocks.add(Material.AIR);
        this.replaceableBlocks.add(Material.CAVE_AIR);
        this.replaceableBlocks.add(Material.GRASS);
        this.replaceableBlocks.add(Material.TALL_GRASS);
        this.replaceableBlocks.add(Material.TALL_SEAGRASS);
        this.replaceableBlocks.add(Material.VINE);
        this.replaceableBlocks.add(Material.CAVE_VINES);
        this.replaceableBlocks.add(Material.WATER);
        this.replaceableBlocks.add(Material.LAVA);
    }

    public BlockProperties explosionResistance(float explosionResistance) {
        this.explosionResistance = explosionResistance;
        return this;
    }

    public BlockProperties destroyTime(float destroyTime) {
        this.destroyTime = destroyTime;
        return this;
    }

    public BlockProperties requireCorrectToolForDrops() {
        this.requireCorrectToolForDrops = true;
        return this;
    }

    public BlockProperties setReplaceableBlocks(Material... replaceableBlocks) {
        this.replaceableBlocks = Arrays.stream(replaceableBlocks).toList();
        return this;
    }

    public BlockProperties addReplaceableBlocks(Material... replaceableBlocks) {
        this.replaceableBlocks.addAll(Arrays.asList(replaceableBlocks));
        return this;
    }

    public BlockProperties notPushable() {
        this.pushable = false;
        return this;
    }

    public List<Material> getReplaceableBlocks() {
        return replaceableBlocks;
    }

    public MaterialColor getMaterialColor() {
        return color;
    }

    public float getExplosionResistance() {
        return explosionResistance;
    }

    public float getDestroyTime() {
        return destroyTime;
    }

    public boolean isRequireCorrectToolForDrops() {
        return requireCorrectToolForDrops;
    }

    public SoundGroup.Block getBlockSounds() {
        return blockSounds;
    }

    public boolean isPushable() {
        return pushable;
    }
}
