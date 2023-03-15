package fr.atlasworld.content.nbt;

import fr.atlasworld.content.ContentAdder;
import fr.atlasworld.content.api.utils.Identifier;

public class ContentNbtKeys {
    public static class Block {
        public static final String blockIdentifierKey = new Identifier(ContentAdder.namespace, "block_id").toString();
    }
}
