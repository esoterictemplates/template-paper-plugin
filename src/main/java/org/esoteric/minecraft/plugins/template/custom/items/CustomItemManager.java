package org.esoteric.minecraft.plugins.template.custom.items;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import org.esoteric.minecraft.plugins.template.TemplatePaperPlugin;

public class CustomItemManager {

  private final Map<CustomItem, AbstractCustomItem> customItemMap = new HashMap<>();

  public CustomItemManager(TemplatePaperPlugin plugin) {
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
