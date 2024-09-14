package org.esoteric_organisation.template_paper_plugin.custom_item;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import org.esoteric_organisation.template_paper_plugin.TemplatePaperPlugin;
import org.esoteric_organisation.template_paper_plugin.custom_item.AbstractCustomItem;
import org.esoteric_organisation.template_paper_plugin.custom_item.CustomItem;

public class CustomItemManager {

  private final Map<CustomItem, AbstractCustomItem> customItemMap = new HashMap<>();

  public CustomItemManager(ExamplePaperPlugin plugin) {
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
