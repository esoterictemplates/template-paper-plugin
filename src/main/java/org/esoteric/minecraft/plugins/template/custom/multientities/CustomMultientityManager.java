package org.esoteric.minecraft.plugins.template.custom.multientities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import org.esoteric.minecraft.plugins.template.TemplatePaperPlugin;

public class CustomMultientityManager {

  private final TemplatePaperPlugin plugin;

  private final Map<CustomMultientity, AbstractCustomMultientity<?>> customMultientityMap = new HashMap<>();

  public TemplatePaperPlugin getPlugin() {
    return plugin;
  }

  public CustomMultientityManager(TemplatePaperPlugin plugin) {
    this.plugin = plugin;
  }

  public void addCustomEntity(CustomMultientity itemId, AbstractCustomMultientity<?> customMultientity) {
    customMultientityMap.put(itemId, customMultientity);
  }

  public AbstractCustomMultientity<?> getAbstractCustomEntity(CustomMultientity entityId) {
    return customMultientityMap.get(entityId);
  }

  public <E extends Entity> List<E> spawnEntity(CustomMultientity entityId, Location location) {
    return (List<E>) getAbstractCustomEntity(entityId).getCustomEntity(location);
  }
}
