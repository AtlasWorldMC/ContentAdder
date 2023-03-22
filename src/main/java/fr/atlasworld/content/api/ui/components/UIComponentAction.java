package fr.atlasworld.content.api.ui.components;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@FunctionalInterface
public interface UIComponentAction<T extends UIComponent> {
    void action(T component, Player player, Inventory inventory);
}
