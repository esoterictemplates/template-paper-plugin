package net.slqmy.template_paper_plugin.custom_multientity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import net.slqmy.template_paper_plugin.TemplatePaperPlugin;
import net.slqmy.template_paper_plugin.custom_multientity.AbstractCustomMultientity;
import net.slqmy.template_paper_plugin.custom_multientity.CustomMultientity;

public class CustomMultientityManager {

  private final Map<CustomMultientity, AbstractCustomMultientity<?>> customEntityMap = new HashMap<>();

  public CustomMultientityManager(TemplatePaperPlugin plugin) {

  }

  public void addCustomEntity(CustomMultientity itemId, AbstractCustomMultientity<?> customItem) {
    customEntityMap.put(itemId, customItem);
  }

  public AbstractCustomMultientity<?> getAbstractCustomEntity(CustomMultientity itemId) {
    return customEntityMap.get(itemId);
  }

  public <E extends Entity> List<E> spawnEntity(CustomMultientity entityId, Location location) {
    return (List<E>) getAbstractCustomEntity(entityId).getCustomEntity(location);
  }
}
