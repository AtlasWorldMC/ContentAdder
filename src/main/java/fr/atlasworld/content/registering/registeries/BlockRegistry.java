package fr.atlasworld.content.registering.registeries;

import fr.atlasworld.content.ContentAdder;
import fr.atlasworld.content.api.Identifier;
import fr.atlasworld.content.api.block.CustomBlock;
import fr.atlasworld.content.api.block.CustomBlockState;
import fr.atlasworld.content.registering.RegistryEntry;
import fr.atlasworld.content.registering.RegistryEventListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BlockRegistry implements RegistryEntry<CustomBlock> {
    private final Identifier registryIdentifier;
    private final Class<CustomBlock> registryType;
    private final List<AbstractMap.SimpleEntry<Identifier, CustomBlock>> registeredEntries;
    private final List<RegistryEventListener<CustomBlock>> eventListeners;
    private final HashMap<Identifier, CustomBlockState> blockState;
    private final CustomBlockState finalBlockState = new CustomBlockState(0, 1, false);

    public BlockRegistry() {
        registryIdentifier = new Identifier(ContentAdder.namespace, "block_registry");
        registryType = CustomBlock.class;
        registeredEntries = new ArrayList<>();
        eventListeners = new ArrayList<>();
        blockState = new HashMap<>();
    }

    @Override
    public @NotNull Identifier getRegistryIdentifier() {
        return registryIdentifier;
    }

    @Override
    public @NotNull Class<CustomBlock> getRegistryType() {
        return registryType;
    }

    @Override
    public void register(Identifier identifier, CustomBlock entry) {
        registeredEntries.add(new AbstractMap.SimpleEntry<>(identifier, entry.setDisplayName(Component.translatable("block." + identifier.getNamespace() + "." + identifier.getName()).decoration(TextDecoration.ITALIC, false))));
        eventListeners.forEach(listener -> listener.onEntryRegister(identifier, entry));
    }

    @Override
    public boolean containsEntry(CustomBlock entry) {
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
    public @Nullable CustomBlock getEntry(Identifier identifier) {
        return registeredEntries.stream().filter(entry -> entry.getKey().equals(identifier)).map(AbstractMap.SimpleEntry::getValue)
                .findFirst().orElse(null);
    }

    @Override
    public @Nullable Identifier getIdentifier(CustomBlock entry) {
        return registeredEntries.stream().filter(rEntry -> rEntry.getValue().equals(entry)).map(AbstractMap.SimpleEntry::getKey)
                .findFirst().orElse(null);
    }

    @Override
    public void clearRegistry() {
        registeredEntries.clear();
        eventListeners.forEach(RegistryEventListener::onRegistryCleared);
    }

    @Override
    public @NotNull List<CustomBlock> getEntries() {
        return registeredEntries.stream().map(AbstractMap.SimpleEntry::getValue).toList();
    }

    @Override
    public @NotNull List<Identifier> getEntriesIdentifier() {
        return registeredEntries.stream().map(AbstractMap.SimpleEntry::getKey).toList();
    }

    @Override
    public void addRegistryEventListener(RegistryEventListener<CustomBlock> listener) {
        eventListeners.add(listener);
    }

    public CustomBlockState getBlockState(Identifier identifier) {
        if (!blockState.containsKey(identifier)) {
            blockState.put(identifier, finalBlockState.getPos());
            finalBlockState.increase();
        }
        return blockState.get(identifier);
    }
}
