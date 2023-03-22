package fr.atlasworld.content.api.ui.components;

import fr.atlasworld.content.api.Identifier;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public abstract class UIComponent {
    private final Identifier identifier;
    private Component displayName;
    private Material parentItem;
    private List<Component> lore;
    private final boolean canComponentBeMovedByPlayer;
    public UIComponent(Identifier identifier, Component defaultName, Material parentItem, boolean canComponentBeMovedByPlayer) {
        this.identifier = identifier;
        this.displayName = defaultName;
        this.parentItem = parentItem;
        this.canComponentBeMovedByPlayer = canComponentBeMovedByPlayer;
    }

    public UIComponent setDisplayName(Component displayName) {
        this.displayName = displayName;
        return this;
    }

    public UIComponent setLore(List<Component> lore) {
        this.lore = lore;
        return this;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Component getDisplayName() {
        return displayName;
    }

    public Material getParentItem() {
        return parentItem;
    }

    public List<Component> getLore() {
        return lore;
    }

    public boolean isCanComponentBeMovedByPlayer() {
        return canComponentBeMovedByPlayer;
    }

    public abstract void onClicked(Player player, Inventory inventory);
}
