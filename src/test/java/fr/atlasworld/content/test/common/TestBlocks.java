package fr.atlasworld.content.test.common;

import fr.atlasworld.content.test.ContentAdderTest;
import fr.atlasworld.content.api.block.BlockProperties;
import fr.atlasworld.content.api.block.CustomBlock;
import fr.atlasworld.content.api.block.material.MaterialColor;
import fr.atlasworld.content.api.item.CustomBlockItem;
import fr.atlasworld.content.api.item.ItemProperties;
import fr.atlasworld.content.api.sound.SoundGroup;
import fr.atlasworld.content.registering.Register;
import fr.atlasworld.content.registering.Registry;
import org.bukkit.Material;

import java.util.function.Supplier;

public class TestBlocks {
    public static final Register<CustomBlock> BLOCKS = new Register<>(Registry.BLOCK, ContentAdderTest.NAMESPACE);

    public static final CustomBlock TEST_BLOCK = registerBlockItem("test_block",
            () -> new CustomBlock(new BlockProperties(MaterialColor.COLOR_GRAY, SoundGroup.Block.STONE)
                    .requireCorrectToolForDrops().addReplaceableBlocks(Material.BEDROCK)), Material.STICK);


    private static CustomBlock registerBlockItem(String name, Supplier<CustomBlock> sup, Material itemParent) {
        CustomBlock block = BLOCKS.register(name, sup);
        TestItems.ITEMS.register(name, () -> new CustomBlockItem(itemParent, new ItemProperties(), block));
        return block;
    }

    public static void register() {
        ContentAdderTest.logger.info("Registered Blocks!");
    }
}
