package net.slqmy.template_paper_plugin.custom_entity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;

import net.slqmy.template_paper_plugin.TemplatePaperPlugin;

public abstract class AbstractCustomEntity<E extends Entity> implements Listener {

  protected final TemplatePaperPlugin plugin;

  private final CustomEntity entityId;
  private final EntityType entityType;

  protected AbstractCustomEntity(TemplatePaperPlugin plugin, CustomEntityManager customEntityManager, CustomEntity entityId, EntityType entityType) {
    this.plugin = plugin;

    this.entityId = entityId;
    this.entityType = entityType;

    Bukkit.getPluginManager().registerEvents(this, plugin);

    customEntityManager.addCustomEntity(entityId, this);
  }

  protected abstract E generateCustomEntity(E baseEntity);

  public E getCustomEntity(Location spawnLocation) {
    E entity = (E) spawnLocation.getWorld().spawnEntity(spawnLocation, entityType);
    entity.getPersistentDataContainer().set(plugin.getCustomEntityIdKey(), PersistentDataType.STRING, entityId.name());
    return generateCustomEntity(entity);
  }

  public boolean isEntity(Entity entity) {
    if (entity == null) {
      return false;
    }

    try {
      return entityId == CustomEntity.valueOf(entity.getPersistentDataContainer().get(plugin.getCustomEntityIdKey(), PersistentDataType.STRING));
    } catch (IllegalArgumentException | NullPointerException exception) {
      return false;
    }
  }
}
