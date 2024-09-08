package net.slqmy.template_paper_plugin.custom_entity.entities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Hoglin;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PiglinBrute;

import net.slqmy.template_paper_plugin.TemplatePaperPlugin;
import net.slqmy.template_paper_plugin.custom_entity.AbstractCustomEntity;
import net.slqmy.template_paper_plugin.custom_entity.CustomEntity;
import net.slqmy.template_paper_plugin.custom_entity.CustomEntityManager;

public class HoglinRider extends AbstractCustomEntity<LivingEntity> {

  public HoglinRider(TemplatePaperPlugin plugin, CustomEntityManager customEntityManager) {
    super(plugin, customEntityManager, CustomEntity.HoglinRider);
  }

  @Override
  protected List<LivingEntity> generateCustomEntity(Location spawnLocation) {
    List<LivingEntity> entities = new ArrayList<>();

    Hoglin hoglin = spawnLocation.getWorld().spawn(spawnLocation, Hoglin.class);

    entities.add(hoglin);

    PiglinBrute hoglinRider = spawnLocation.getWorld().spawn(spawnLocation, PiglinBrute.class);

    hoglin.addPassenger(hoglinRider);

    entities.add(hoglinRider);

    return entities;
  }
}
