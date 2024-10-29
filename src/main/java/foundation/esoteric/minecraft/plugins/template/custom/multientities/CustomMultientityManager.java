package foundation.esoteric.minecraft.plugins.template.custom.multientities;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import foundation.esoteric.minecraft.plugins.template.PaperTemplatePlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomMultientityManager {

  private final PaperTemplatePlugin plugin;

  private final Map<CustomMultientity, AbstractCustomMultientity<?>> customMultientityMap = new HashMap<>();

  public PaperTemplatePlugin getPlugin() {
    return plugin;
  }

  public CustomMultientityManager(PaperTemplatePlugin plugin) {
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
