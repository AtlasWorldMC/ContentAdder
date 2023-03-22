package fr.atlasworld.content.api.ui;

import fr.atlasworld.content.api.ui.components.Button;
import fr.atlasworld.content.api.ui.components.SelectBox;
import fr.atlasworld.content.api.ui.components.UIComponent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.AbstractMap;
import java.util.HashMap;

public class CustomGui {
    private Inventory inventory;
    private HashMap<UIComponent, AbstractMap.SimpleEntry<Integer, Integer>> components = new HashMap<>();
    public CustomGui(InventoryHolder holder, int size){
        this.inventory = Bukkit.createInventory(holder, size);
    }

    public CustomGui(InventoryHolder holder, int xSize, int ySize){
        this.inventory = Bukkit.createInventory(holder, (xSize*ySize));
    }

    public int getSize() {
        return this.inventory.getSize();
    }
}
