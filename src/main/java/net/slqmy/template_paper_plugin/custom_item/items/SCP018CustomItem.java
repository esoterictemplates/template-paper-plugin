package net.slqmy.template_paper_plugin.custom_item.items;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import net.slqmy.template_paper_plugin.TemplatePaperPlugin;
import net.slqmy.template_paper_plugin.custom_item.AbstractCustomItem;
import net.slqmy.template_paper_plugin.language.Message;

public class SCP018CustomItem extends AbstractCustomItem {

  private final NamespacedKey isEggEntityScp018Key;

  public SCP018CustomItem(TemplatePaperPlugin plugin) {
    super(plugin, "scp_018", Material.EGG);

    isEggEntityScp018Key = new NamespacedKey(plugin, "is_scp_018");
  }

  @Override
  public ItemStack getCustomItem(Player player) {
    ItemStack item = getBaseCustomItem();

    item.editMeta((meta) -> meta.displayName(plugin.getLanguageManager().getMessage(Message.SCP018, player)));

    return item;
  }

  @EventHandler
  public void onCrouch(PlayerToggleSneakEvent event) {
    Player player = event.getPlayer();
    PlayerInventory inventory = player.getInventory();

    inventory.addItem(getCustomItem(player));
  }

  @EventHandler
  public void onScp018Throw(ProjectileLaunchEvent event) {
    Projectile projectile = event.getEntity();

    if (!(projectile instanceof Egg egg)) {
      return;
    }

    if (isItem(egg.getItem())) {
      setIsEntityScp018(egg);
    }
  }

  @EventHandler
  public void onScp018Bounce(ProjectileHitEvent event) {
    Projectile projectile = event.getEntity();

    Entity hitEntity = event.getHitEntity();
    Block hitBlock = event.getHitBlock();

    if (isEntityScp018(projectile)) {
      if (hitEntity != null) {
        event.setCancelled(true);
        Vector velocity = projectile.getVelocity();
        velocity.setY(-2.0F * velocity.getY());    
        projectile.setVelocity(velocity);
      } else if (hitBlock != null) {
        BlockFace hitFace = event.getHitBlockFace();
        Vector normalVector = hitFace.getDirection();

        Vector velocity = projectile.getVelocity();
        Vector newVelocity = velocity.subtract(normalVector.multiply(2.0F * velocity.dot(normalVector)));

        Egg egg = (Egg) projectile.getWorld().spawnEntity(projectile.getLocation(), EntityType.EGG);
        egg.setVelocity(newVelocity);
        setIsEntityScp018(egg);
      }
    }
  }

  @EventHandler
  public void onScp018Hatch(PlayerEggThrowEvent event) {
    if (isEntityScp018(event.getEgg())) {
      event.setHatching(false);
    }
  }

  @EventHandler
  public void onScp018Hatch() {

  }

  private void setIsEntityScp018(Egg egg) {
    egg.getPersistentDataContainer().set(isEggEntityScp018Key, PersistentDataType.BOOLEAN, true);
  }

  private boolean isEntityScp018(Entity egg) {
    Boolean isScp018 = egg.getPersistentDataContainer().get(isEggEntityScp018Key, PersistentDataType.BOOLEAN);

    return (isScp018 == null ? false : isScp018);
  }
}
