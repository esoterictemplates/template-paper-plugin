package org.esoteric.minecraft.plugins.template.util.types;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.checkerframework.checker.nullness.qual.NonNull;

public class BlockLocation {

  private String worldName;

  private int x;
  private int y;
  private int z;

  public String getWorldName() {
    return worldName;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getZ() {
    return z;
  }

  public BlockLocation(String worldName, int x, int y, int z) {
    this.worldName = worldName;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public BlockLocation(@NonNull Location location) {
    this(location.getWorld().getName(), (int) location.getX(), (int) location.getY(), (int) location.getZ());
  }

  public BlockLocation(@NonNull Block block) {
    this(block.getLocation());
  }
}
