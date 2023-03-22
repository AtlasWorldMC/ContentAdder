package fr.atlasworld.content.api.ui.components;

import fr.atlasworld.content.ContentAdder;
import fr.atlasworld.content.api.Identifier;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class SelectBox extends UIComponent{
    private UIComponentAction<SelectBox> selectedAction;
    private UIComponentAction<SelectBox> unSelectedAction;
    private boolean selected;
    public SelectBox(Component name) {
        super(new Identifier(ContentAdder.namespace, "select_box"), name, Material.STICK, false);
        this.selected = false;
    }
    public SelectBox(Component name, boolean selected) {
        super(new Identifier(ContentAdder.namespace, "select_box"), name, Material.STICK, false);
        this.selected = selected;
    }

    @Override
    public void onClicked(Player player, Inventory inventory) {
        if (!this.selected) {
            this.selected = true;
            if (selectedAction != null) {
                this.selectedAction.action(this, player, inventory);
            }
        } else {
            this.selected = false;
            if (selectedAction != null) {
                this.unSelectedAction.action(this, player, inventory);
            }
        }
    }

    public SelectBox setOnSelected(UIComponentAction action) {
        this.selectedAction = action;
        return this;
    }

    public SelectBox setOnUnselected(UIComponentAction action) {
        this.unSelectedAction = action;
        return this;
    }
}
