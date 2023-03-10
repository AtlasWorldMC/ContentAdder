package fr.atlasworld.content.test.listeners;

import fr.atlasworld.content.test.common.TestBlocks;
import fr.atlasworld.content.test.common.TestItems;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;
import java.util.UUID;

public class PlayerEvents implements Listener {
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        event.getPlayer().getInventory().addItem(TestItems.TEST_ITEM.getItem());
        event.getPlayer().getInventory().addItem(Objects.requireNonNull(TestBlocks.TEST_BLOCK.getAsItem()));
    }
}
