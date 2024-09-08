package net.slqmy.template_paper_plugin.custom_item;

import java.util.HashMap;
import java.util.Map;

import net.slqmy.template_paper_plugin.custom_item.AbstractCustomItem;
import net.slqmy.template_paper_plugin.custom_item.CustomItem;
import net.slqmy.template_paper_plugin.TemplatePaperPlugin;

public class CustomItemManager {

  private Map<CustomItem, AbstractCustomItem> customItemMap = new HashMap<>();

  public CustomItemManager(TemplatePaperPlugin plugin) {
  }

  public void addCustomItem(CustomItem itemId, AbstractCustomItem customItem) {
    customItemMap.put(itemId, customItem);
  }

  public AbstractCustomItem getAbstractCustomItem(CustomItem itemId) {
    return customItemMap.get(itemId);
  }
}
