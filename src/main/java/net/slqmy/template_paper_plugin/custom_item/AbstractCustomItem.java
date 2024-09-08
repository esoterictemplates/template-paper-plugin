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

  protected final String itemId;
  protected final Material material;

  private final NamespacedKey itemIdKey;

  public AbstractCustomItem(TemplatePaperPlugin plugin, String itemId, Material material) {
    this.plugin = plugin;

    this.itemId = itemId;
    this.material = material;

    itemIdKey = new NamespacedKey(plugin, itemId);

    Bukkit.getPluginManager().registerEvents(this, plugin);
  }

  public abstract ItemStack getCustomItem(Player player);

  protected ItemStack getBaseCustomItem() {
    ItemStack item = new ItemStack(material);
    item.editMeta((meta) -> meta.getPersistentDataContainer().set(itemIdKey, PersistentDataType.STRING, itemId));
    return item;
  }

  public boolean isItem(ItemStack itemStack) {
    if (itemStack == null) {
      return false;
    }

    if (!itemStack.hasItemMeta()) {
      return false;
    }

    return itemId.equals(itemStack.getItemMeta().getPersistentDataContainer().get(itemIdKey, PersistentDataType.STRING));
  }
}
