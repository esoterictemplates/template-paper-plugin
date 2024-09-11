package dev.esoteric_organisation.example_paper_plugin.custom_multientity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import dev.esoteric_organisation.example_paper_plugin.ExamplePaperPlugin;
import dev.esoteric_organisation.example_paper_plugin.custom_multientity.AbstractCustomMultientity;
import dev.esoteric_organisation.example_paper_plugin.custom_multientity.CustomMultientity;

public class CustomMultientityManager {

  private final Map<CustomMultientity, AbstractCustomMultientity<?>> customMultientityMap = new HashMap<>();

  public CustomMultientityManager(ExamplePaperPlugin plugin) {

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
