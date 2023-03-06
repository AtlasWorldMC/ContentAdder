package fr.atlasworld.content.networking;


import com.comphenix.protocol.ProtocolManager;

public class NetworkHandler {

    private static ProtocolManager manager;

    public static void registerNetwork(ProtocolManager pManager) {
        manager = pManager;
    }
}
