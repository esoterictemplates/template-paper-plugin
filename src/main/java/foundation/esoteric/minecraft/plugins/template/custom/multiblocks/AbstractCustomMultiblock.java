package foundation.esoteric.minecraft.plugins.template.custom.multiblocks;

import foundation.esoteric.minecraft.plugins.library.utility.types.BlockLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import foundation.esoteric.minecraft.plugins.template.PaperTemplatePlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCustomMultiblock implements Listener {

  protected final PaperTemplatePlugin plugin;

  private final List<List<BlockLocation>> multiblocks = new ArrayList<>();

  public List<List<BlockLocation>> getMultiblocks() {
    return multiblocks;
  }

  public void addMultiblocks(List<List<BlockLocation>> addedMultiblocks) {
    multiblocks.addAll(addedMultiblocks);
  }

  public AbstractCustomMultiblock(@NotNull CustomMultiblockManager customMultiblockManager, CustomMultiblock multiblockId) {
    this.plugin = customMultiblockManager.getPlugin();

    Bukkit.getPluginManager().registerEvents(this, plugin);

    customMultiblockManager.addCustomMultiblock(multiblockId, this);
  }

  protected abstract List<Block> generateCustomMultiblock(Location placeLocation);

  public List<Block> getCustomMultiblock(Location placeLocation) {
    List<Block> multiblock = generateCustomMultiblock(placeLocation);

    multiblocks.add(multiblock.stream().map(BlockLocation::new).toList());

    return multiblock;
  }

  public boolean isBlock(Block block) {
    for (List<BlockLocation> multiblock : multiblocks) {
      if (multiblock.contains(new BlockLocation(block))) {
        return true;
      }
    }

    return false;
  }
}
