package fr.atlasworld.content.api.item;

public class ItemProperties {
    private boolean fireResistant;
    private int maxStackSize;

    public ItemProperties() {
        this.fireResistant = false;
        this.maxStackSize = 64;
    }

    public ItemProperties setFireResistant(boolean fireResistant) {
        this.fireResistant = fireResistant;
        return this;
    }

    public ItemProperties setMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
        return this;
    }

    public boolean isFireResistant() {
        return fireResistant;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }
}
