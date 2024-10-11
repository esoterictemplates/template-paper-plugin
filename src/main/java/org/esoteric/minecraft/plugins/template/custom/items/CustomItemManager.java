package org.esoteric.minecraft.plugins.template.custom.items;

import org.bukkit.entity.Player;
import org.esoteric.minecraft.plugins.template.PaperTemplatePlugin;

import java.util.HashMap;
import java.util.Map;

public class CustomItemManager {

  private final PaperTemplatePlugin plugin;

  private final Map<CustomItem, AbstractCustomItem> customItemMap = new HashMap<>();

  public PaperTemplatePlugin getPlugin() {
    return plugin;
  }

  public CustomItemManager(PaperTemplatePlugin plugin) {
    this.plugin = plugin;
  }

  public void addCustomItem(CustomItem itemId, AbstractCustomItem customItem) {
    customItemMap.put(itemId, customItem);
  }

  public AbstractCustomItem getAbstractCustomItem(CustomItem itemId) {
    return customItemMap.get(itemId);
  }

  public void giveCustomItem(CustomItem itemId, Player player) {
    customItemMap.get(itemId).give(player);
  }
}
