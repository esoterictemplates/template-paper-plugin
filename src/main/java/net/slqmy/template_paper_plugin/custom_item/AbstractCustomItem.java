package net.slqmy.template_paper_plugin.custom_item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import net.slqmy.template_paper_plugin.TemplatePaperPlugin;

public abstract class AbstractCustomItem implements Listener {

  protected final TemplatePaperPlugin plugin;

  private final CustomItem itemId;
  private final Material material;

  private final NamespacedKey itemIdKey;

  public AbstractCustomItem(TemplatePaperPlugin plugin, CustomItemManager customItemManager, CustomItem itemId, Material material) {
    this.plugin = plugin;

    this.itemId = itemId;
    this.material = material;

    itemIdKey = new NamespacedKey(plugin, itemId.name());

    Bukkit.getPluginManager().registerEvents(this, plugin);

    customItemManager.addCustomItem(itemId, this);
  }

  protected abstract ItemStack generateCustomItem(ItemStack baseCustomItem, Player player);

  public ItemStack getCustomItem(Player player) {
    ItemStack item = new ItemStack(material);
    item.editMeta((meta) -> meta.getPersistentDataContainer().set(itemIdKey, PersistentDataType.STRING, itemId.name()));
    return generateCustomItem(item, player);
  }

  public boolean isItem(ItemStack itemStack) {
    if (itemStack == null) {
      return false;
    }

    if (!itemStack.hasItemMeta()) {
      return false;
    }

    String dataContainerItemIdValue = itemStack.getItemMeta().getPersistentDataContainer().get(itemIdKey, PersistentDataType.STRING);
    if (dataContainerItemIdValue == null) {
      return false;
    }

    try {
      return itemId == CustomItem.valueOf(dataContainerItemIdValue);
    } catch (IllegalArgumentException exception) {
      return false;
    }
  }

  public void give(Player player) {
    player.getInventory().addItem(getCustomItem(player));
  }
}
