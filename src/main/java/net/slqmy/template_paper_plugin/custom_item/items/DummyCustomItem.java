package net.slqmy.template_paper_plugin.custom_item.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.kyori.adventure.text.Component;
import net.slqmy.template_paper_plugin.TemplatePaperPlugin;
import net.slqmy.template_paper_plugin.custom_item.AbstractCustomItem;
import net.slqmy.template_paper_plugin.custom_item.CustomItem;
import net.slqmy.template_paper_plugin.custom_item.CustomItemManager;

public class DummyCustomItem extends AbstractCustomItem {

  public DummyCustomItem(TemplatePaperPlugin plugin, CustomItemManager customItemManager) {
    super(plugin, customItemManager, CustomItem.DUMMY_CUSTOM_ITEM, Material.DIAMOND);
  }

  @Override
  protected ItemStack generateCustomItem(ItemStack baseCustomItem, Player player) {
    baseCustomItem.editMeta((meta) -> meta.displayName(Component.text("Dummy Custom Item")));
    return baseCustomItem;
  }
}
