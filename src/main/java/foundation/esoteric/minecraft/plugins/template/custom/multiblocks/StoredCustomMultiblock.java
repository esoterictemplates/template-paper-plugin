package foundation.esoteric.minecraft.plugins.template.custom.multiblocks;

import foundation.esoteric.minecraft.plugins.library.utility.types.BlockLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map.Entry;

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

  public StoredCustomMultiblock(@NotNull Entry<CustomMultiblock, AbstractCustomMultiblock> multiblockInfo) {
    this(multiblockInfo.getValue().getMultiblocks(), multiblockInfo.getKey());
  }
}
