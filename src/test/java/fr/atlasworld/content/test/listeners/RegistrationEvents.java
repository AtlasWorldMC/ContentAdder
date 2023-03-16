package fr.atlasworld.content.test.listeners;

import fr.atlasworld.content.events.registration.RegisterBlockEvent;
import fr.atlasworld.content.events.registration.RegisterItemEvent;
import fr.atlasworld.content.test.common.TestBlocks;
import fr.atlasworld.content.test.common.TestItems;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RegistrationEvents implements Listener {
    @EventHandler
    public void onItemRegister(RegisterItemEvent event) {
        TestItems.register();
    }

    @EventHandler
    public void onBlockRegister(RegisterBlockEvent event) {
        TestBlocks.register();
    }
}
