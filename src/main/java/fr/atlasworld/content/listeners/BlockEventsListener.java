package fr.atlasworld.content.listeners;

import fr.atlasworld.content.ContentAdder;
import fr.atlasworld.content.api.block.CustomBlock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.NotePlayEvent;

public class BlockEventsListener implements Listener {
    @EventHandler
    public void onPlayNote(NotePlayEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event) {
        if (event.getBlock().getType().equals(Material.NOTE_BLOCK) && !CustomBlock.isCustomBlock(event.getBlock())) {
            event.getPlayer().sendMessage(ContentAdder.prefix.append(Component.translatable("chat." + ContentAdder.namespace + ".can_not_place_note_block").color(TextColor.color(192, 0, 0))));
            event.setCancelled(true);
        }
    }
}
