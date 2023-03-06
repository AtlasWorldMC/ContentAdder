package fr.atlasworld.content.registering;

import fr.atlasworld.content.api.Identifier;
public abstract class RegistryEventListener<I> {
    public void onEntryRegister(Identifier identifier, I entry) {}
    public void onRegistryCleared() {}
}
