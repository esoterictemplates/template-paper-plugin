package net.slqmy.template_paper_plugin.custom_multiblock.blocks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import net.slqmy.template_paper_plugin.TemplatePaperPlugin;
import net.slqmy.template_paper_plugin.custom_multiblock.AbstractCustomMultiblock;
import net.slqmy.template_paper_plugin.custom_multiblock.CustomMultiblock;
import net.slqmy.template_paper_plugin.custom_multiblock.CustomMultiblockManager;

public class MultiFurnace extends AbstractCustomMultiblock {

  public MultiFurnace(TemplatePaperPlugin plugin, CustomMultiblockManager customMultiblockManager) {
    super(plugin, customMultiblockManager, CustomMultiblock.MultiFurnace);
  }

  @Override
  protected List<Block> generateCustomMultiblock(Location placeLocation) {
    List<Block> result = new ArrayList<>();

    placeLocation.getBlock().setType(Material.FURNACE);

    result.add(placeLocation.getBlock());

    placeLocation.add(0, 1, 0).getBlock().setType(Material.BLAST_FURNACE);

    result.add(placeLocation.getBlock());

    return result;
  }
}
