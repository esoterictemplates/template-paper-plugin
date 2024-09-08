package net.slqmy.template_paper_plugin.custom_multiblock;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;

import net.slqmy.template_paper_plugin.custom_multiblock.AbstractCustomMultiblock;
import net.slqmy.template_paper_plugin.custom_multiblock.CustomMultiblock;
import net.slqmy.template_paper_plugin.TemplatePaperPlugin;

public class CustomMultiblockManager {

  private final TemplatePaperPlugin plugin;

  private final Map<CustomMultiblock, AbstractCustomMultiblock> customMultiblockMap = new HashMap<>();

  public CustomMultiblockManager(TemplatePaperPlugin plugin) {
    this.plugin = plugin;
  }

  public void addCustomMultiblock(CustomMultiblock multiblockId, AbstractCustomMultiblock abstractCustomMultiblock) {
    customMultiblockMap.put(multiblockId, abstractCustomMultiblock);
  }

  public AbstractCustomMultiblock getAbstractCustomMultiblock(CustomMultiblock multiblockId) {
    return customMultiblockMap.get(multiblockId);
  }

  public void placeCustomMultiblock(CustomMultiblock multiblockId, Location location) {
    customMultiblockMap.get(multiblockId).getCustomMultiblock(location);
  }
}
