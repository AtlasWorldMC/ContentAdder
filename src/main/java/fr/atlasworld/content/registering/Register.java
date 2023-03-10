package fr.atlasworld.content.registering;

import fr.atlasworld.content.api.utils.Identifier;

import java.util.function.Supplier;

public class Register<T> {
    private RegistryEntry<T> registry;
    private String namespace;
    public Register(RegistryEntry<T> registry, String namespace) {
        this.registry = registry;
        this.namespace = namespace;
    }

    public T register(String name, Supplier<T> supplier) {
        T ret = supplier.get();
        registry.register(new Identifier(namespace, name), ret);
        return ret;
    }
}
