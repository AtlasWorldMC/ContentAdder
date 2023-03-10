package fr.atlasworld.content.test.common.item;

import fr.atlasworld.content.api.item.CustomItem;
import fr.atlasworld.content.api.item.ItemProperties;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;

public class MyCustomTestItem extends CustomItem {
    public MyCustomTestItem(Material parent, ItemProperties properties) {
        super(parent, properties);
    }

    @Override
    public void onItemDestroyed(Item itemEntity) {
        Bukkit.broadcast(Component.text("ITEM DESTROYED!"));
    }
}
