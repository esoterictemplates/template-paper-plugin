package dev.esoteric_organisation.example_paper_plugin.custom_item;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import dev.esoteric_organisation.example_paper_plugin.ExamplePaperPlugin;
import dev.esoteric_organisation.example_paper_plugin.custom_item.AbstractCustomItem;
import dev.esoteric_organisation.example_paper_plugin.custom_item.CustomItem;

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
