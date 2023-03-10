package fr.atlasworld.content.registering;

import com.pushtorefresh.javac_warning_annotation.Warning;
import fr.atlasworld.content.api.utils.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface RegistryEntry<I> {
    @NotNull Identifier getRegistryIdentifier();
    @NotNull Class<I> getRegistryType();
    void register(Identifier identifier, I entry);
    boolean containsEntry(I entry);
    boolean containsIdentifier(Identifier identifier);
    boolean isEmpty();
    @Nullable I getEntry(Identifier identifier);
    @Nullable Identifier getIdentifier(I entry);
    @Warning("Clearing a Registry could break features!")
    void clearRegistry();
    @NotNull List<I> getEntries();
    @NotNull List<Identifier> getEntriesIdentifier();
    void addRegistryEventListener(RegistryEventListener<I> listener);
}
