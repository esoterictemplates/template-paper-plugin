package net.slqmy.template_paper_plugin.custom_entity.entities;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Squid;
import org.bukkit.util.Vector;

import net.slqmy.template_paper_plugin.TemplatePaperPlugin;
import net.slqmy.template_paper_plugin.custom_entity.AbstractCustomEntity;
import net.slqmy.template_paper_plugin.custom_entity.CustomEntity;
import net.slqmy.template_paper_plugin.custom_entity.CustomEntityManager;

public class DummyCustomEntity extends AbstractCustomEntity<Squid> {

  public DummyCustomEntity(TemplatePaperPlugin plugin, CustomEntityManager customEntityManager) {
    super(plugin, customEntityManager, CustomEntity.EXPLOSIVE_SQUID, EntityType.SQUID);
  }

  @Override
  protected Entity generateCustomEntity(Squid baseEntity) {
    baseEntity.setVelocity(new Vector(0D, 50D, 0D));
    return baseEntity;
  }
}
