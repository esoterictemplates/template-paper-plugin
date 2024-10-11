package org.esoteric.minecraft.plugins.template.custom.multiblocks;

import java.util.List;
import java.util.Map.Entry;

import org.esoteric.minecraft.plugins.template.util.types.BlockLocation;
import org.checkerframework.checker.nullness.qual.NonNull;

public class StoredCustomMultiblock {
  private final List<List<BlockLocation>> blockLocations;
  private final CustomMultiblock multiblockId;

  public List<List<BlockLocation>> getBlockLocations() {
    return blockLocations;
  }

  public CustomMultiblock getMultiblockId() {
    return multiblockId;
  }

  public StoredCustomMultiblock(List<List<BlockLocation>> blockLocations, CustomMultiblock multiblockId) {
    this.blockLocations = blockLocations;
    this.multiblockId = multiblockId;
  }

  public StoredCustomMultiblock(@NonNull Entry<CustomMultiblock, AbstractCustomMultiblock> multiblockInfo) {
    this(multiblockInfo.getValue().getMultiblocks(), multiblockInfo.getKey());
  } 
}
