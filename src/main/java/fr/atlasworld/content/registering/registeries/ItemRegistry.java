package fr.atlasworld.content.registering.registeries;

import fr.atlasworld.content.ContentAdder;
import fr.atlasworld.content.api.Identifier;
import fr.atlasworld.content.api.item.CustomItem;
import fr.atlasworld.content.registering.RegistryEntry;
import fr.atlasworld.content.registering.RegistryEventListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemRegistry implements RegistryEntry<CustomItem> {
    private final Identifier registryIdentifier;
    private final Class<CustomItem> registryType;
    private final List<AbstractMap.SimpleEntry<Identifier, CustomItem>> registeredEntries;
    private final List<RegistryEventListener<CustomItem>> eventListeners;
    private final HashMap<Material, Integer> materialCustomModelData;

    public ItemRegistry() {
        this.registryIdentifier = new Identifier(ContentAdder.namespace, "item_registry");
        this.registryType = CustomItem.class;
        this.registeredEntries = new ArrayList<>();
        this.eventListeners = new ArrayList<>();
        this.materialCustomModelData = new HashMap<>();
    }

    @Override
    public @NotNull Identifier getRegistryIdentifier() {
        return registryIdentifier;
    }

    @Override
    public @NotNull Class<CustomItem> getRegistryType() {
        return registryType;
    }

    @Override
    public void register(Identifier identifier, CustomItem entry) {
        registeredEntries.add(new AbstractMap.SimpleEntry<>(identifier, entry.setDisplayName(Component.translatable("item." + identifier.getNamespace() + "." + identifier.getName()).decoration(TextDecoration.ITALIC, false))));
        eventListeners.forEach(listener -> listener.onEntryRegister(identifier, entry));
    }

    @Override
    public boolean containsEntry(CustomItem entry) {
        return registeredEntries.stream().map(AbstractMap.SimpleEntry::getValue).anyMatch(item -> item.equals(entry));
    }

    @Override
    public boolean containsIdentifier(Identifier identifier) {
        return registeredEntries.stream().map(AbstractMap.SimpleEntry::getKey).anyMatch(id -> id.equals(identifier));
    }

    @Override
    public boolean isEmpty() {
        return registeredEntries.isEmpty();
    }

    @Override
    public @Nullable CustomItem getEntry(Identifier identifier) {
        return registeredEntries.stream().filter(entry -> entry.getKey().equals(identifier)).map(AbstractMap.SimpleEntry::getValue)
                .findFirst().orElse(null);
    }

    @Override
    public @Nullable Identifier getIdentifier(CustomItem entry) {
        return registeredEntries.stream().filter(rEntry -> rEntry.getValue().equals(entry)).map(AbstractMap.SimpleEntry::getKey)
                .findFirst().orElse(null);
    }

    @Override
    public void clearRegistry() {
        registeredEntries.clear();
        eventListeners.forEach(RegistryEventListener::onRegistryCleared);
    }

    @Override
    public @NotNull List<CustomItem> getEntries() {
        return registeredEntries.stream().map(AbstractMap.SimpleEntry::getValue).toList();
    }

    @Override
    public @NotNull List<Identifier> getEntriesIdentifier() {
        return registeredEntries.stream().map(AbstractMap.SimpleEntry::getKey).toList();
    }

    @Override
    public void addRegistryEventListener(RegistryEventListener<CustomItem> listener) {
        eventListeners.add(listener);
    }

    public int getCustomModelData(Material material) {
        return materialCustomModelData.compute(material, (k, v) -> (v == null) ? 1 : v + 1);
    }
}
