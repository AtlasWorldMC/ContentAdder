package fr.atlasworld.content.world;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public class WorldUtils {
    public static Location applyFaceMod(Location location, BlockFace face) {
        location.setX(location.getX() + face.getModX());
        location.setY(location.getY() + face.getModY());
        location.setZ(location.getZ() + face.getModZ());
        return location;
    }
}
