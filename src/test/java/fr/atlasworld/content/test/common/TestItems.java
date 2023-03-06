package fr.atlasworld.content.test.common;

import fr.atlasworld.content.test.ContentAdderTest;
import fr.atlasworld.content.api.item.CustomItem;
import fr.atlasworld.content.api.item.ItemProperties;
import fr.atlasworld.content.registering.Register;
import fr.atlasworld.content.registering.Registry;
import org.bukkit.Material;

public class TestItems {
    public static final Register<CustomItem> ITEMS = new Register<>(Registry.ITEM, ContentAdderTest.NAMESPACE);

    public static final CustomItem TEST_ITEM = ITEMS.register("test_item",
            () -> new CustomItem(Material.STICK, new ItemProperties()));

    public static void register() {
        ContentAdderTest.logger.info("Registered Items!");
    }
}
