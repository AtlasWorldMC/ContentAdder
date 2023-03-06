package fr.atlasworld.content.nbt;

import fr.atlasworld.content.ContentAdder;
import fr.atlasworld.content.api.Identifier;

public class BlockNbtKeys {
    public static final String blockIdentifierKey = new Identifier(ContentAdder.namespace, "block_id").toString();
}
