package fr.atlasworld.content.api.ui.components;

import fr.atlasworld.content.ContentAdder;
import fr.atlasworld.content.api.Identifier;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Button extends UIComponent{
    private UIComponentAction<Button> action;
    public Button(Component name) {
        super(new Identifier(ContentAdder.namespace, "button"), name, Material.STICK, false);
    }

    public Button setOnButtonClicked(UIComponentAction action) {
        this.action = action;
        return this;
    }

    @Override
    public void onClicked(Player player, Inventory inventory) {
        if (this.action != null) {
            this.action.action(this, player, inventory);
        }
    }
}
