package net.slqmy.template_paper_plugin.custom_multiblock;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import net.slqmy.template_paper_plugin.TemplatePaperPlugin;

public abstract class AbstractCustomMultiblock implements Listener {

  private final TemplatePaperPlugin plugin;

  private final CustomMultiblock multiblockId;

  public AbstractCustomMultiblock(TemplatePaperPlugin plugin, CustomMultiblockManager customMultiblockManager, CustomMultiblock multiblockId) {
    this.plugin = plugin;

    this.multiblockId = multiblockId;

    Bukkit.getPluginManager().registerEvents(this, plugin);

    customMultiblockManager.addCustomMultiblock(multiblockId, this);
  }

  protected abstract List<Block> generateCustomMultiblock(Location placeLocation);

  public List<Block> getCustomMultiblock(Location placeLocation) {
    List<Block> multiblock = generateCustomMultiblock(placeLocation);

    for (Block block : multiblock) {
      block.setMetadata(plugin.getCustomMultiblockIdKey(), new FixedMetadataValue(plugin, multiblockId.name()));
    }

    return multiblock;
  }

  public boolean isBlock(Block block) {
    List<MetadataValue> values = block.getMetadata(plugin.getCustomMultiblockIdKey());

    if (values.size() == 0) {
      return false;
    }

    return multiblockId.name().equals(values.get(0).asString());
  }
}
