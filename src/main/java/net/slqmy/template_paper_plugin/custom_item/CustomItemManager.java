package net.slqmy.template_paper_plugin.custom_item;

import net.slqmy.template_paper_plugin.TemplatePaperPlugin;
import net.slqmy.template_paper_plugin.custom_item.items.SCP018CustomItem;

public class CustomItemManager {

  public CustomItemManager(TemplatePaperPlugin plugin) {
    new SCP018CustomItem(plugin);
  }
}
