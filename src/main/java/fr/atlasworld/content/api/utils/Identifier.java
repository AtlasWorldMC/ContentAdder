package fr.atlasworld.content.api.utils;

public class Identifier {
    private final String namespace;
    private final String name;

    public Identifier(String namespace, String name) {
        this.namespace = namespace;
        this.name = name;
    }

    public Identifier(String asString) {
        if (!asString.contains(":")) throw new IllegalArgumentException("Invalid Identifier! it must be 'namespace:name': " + asString);
        String[] splitIdentifier = asString.split(":");
        if (splitIdentifier.length > 2) throw new IllegalArgumentException("Only one ':' can be used in a Identifier! 'namespace:name'!");
        this.namespace = splitIdentifier[0];
        this.name = splitIdentifier[1];
    }

    public String getNamespace() {
        return namespace;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return namespace + ":" + name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Identifier idObj) {
            return idObj.namespace.equals(this.namespace) && idObj.name.equals(this.name);
        }
        return false;
    }

    public static boolean isValidIdentifier(String str) {
        if (!str.contains(":")) return false;
        String[] splitIdentifier = str.split(":");
        return splitIdentifier.length <= 2;
    }
}
