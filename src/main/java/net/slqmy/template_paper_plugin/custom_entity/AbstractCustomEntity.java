package net.slqmy.template_paper_plugin.custom_entity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;

import net.slqmy.template_paper_plugin.TemplatePaperPlugin;

public abstract class AbstractCustomEntity<E extends Entity> implements Listener {

  protected final TemplatePaperPlugin plugin;

  private final CustomEntity entityId;
  private final EntityType entityType;

  private final NamespacedKey entityIdKey;

  protected AbstractCustomEntity(TemplatePaperPlugin plugin, CustomEntityManager customEntityManager, CustomEntity entityId, EntityType entityType) {
    this.plugin = plugin;

    this.entityId = entityId;
    this.entityType = entityType;

    entityIdKey = new NamespacedKey(plugin, entityId.name());

    Bukkit.getPluginManager().registerEvents(this, plugin);

    customEntityManager.addCustomEntity(entityId, this);
  }

  protected abstract Entity generateCustomEntity(E baseEntity);

  public Entity getCustomEntity(Location spawnLocation) {
    E entity = (E) spawnLocation.getWorld().spawnEntity(spawnLocation, entityType);
    entity.getPersistentDataContainer().set(entityIdKey, PersistentDataType.STRING, entityId.name());
    return generateCustomEntity(entity);
  }

  public boolean isEntity(Entity entity) {
    if (entity == null) {
      return false;
    }

    return entityType == EntityType.valueOf(entity.getPersistentDataContainer().get(entityIdKey, PersistentDataType.STRING));
  }
}
