package net.slqmy.template_paper_plugin;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import net.slqmy.template_paper_plugin.commands.GiveCustomItemCommand;
import net.slqmy.template_paper_plugin.commands.SetLanguageCommand;
import net.slqmy.template_paper_plugin.custom_entity.CustomEntityManager;
import net.slqmy.template_paper_plugin.custom_item.CustomItemManager;
import net.slqmy.template_paper_plugin.data.player.PlayerDataManager;
import net.slqmy.template_paper_plugin.language.LanguageManager;

@DefaultQualifier(NonNull.class)
public final class TemplatePaperPlugin extends JavaPlugin {

  private PlayerDataManager playerDataManager;
  private LanguageManager languageManager;
  private CustomItemManager customItemManager;
  private CustomEntityManager customEntityManager;

  private final NamespacedKey customItemIdKey = new NamespacedKey(this, "custom_item_id");
  private final NamespacedKey customEntityIdKey = new NamespacedKey(this, "custom_entity_id");

  public PlayerDataManager getPlayerDataManager() {
    return playerDataManager;
  }

  public LanguageManager getLanguageManager() {
    return languageManager;
  }

  public CustomItemManager getCustomItemManager() {
    return customItemManager;
  }

  public CustomEntityManager getCustomEntityManager() {
    return customEntityManager;
  }

  public NamespacedKey getCustomItemIdKey() {
    return customItemIdKey;
  }

  public NamespacedKey getCustomEntityIdKey() {
    return customEntityIdKey;
  }

  @Override
  public void onEnable() {
    getDataFolder().mkdir();
    saveDefaultConfig();

    CommandAPIBukkitConfig commandAPIConfig = new CommandAPIBukkitConfig(this);

    CommandAPI.onLoad(commandAPIConfig);
    CommandAPI.onEnable();

    playerDataManager = new PlayerDataManager(this);
    languageManager = new LanguageManager(this);
    customItemManager = new CustomItemManager(this);
    customEntityManager = new CustomEntityManager(this);

    new SetLanguageCommand(this);

    new GiveCustomItemCommand(this);
  }

  @Override
  public void onDisable() {
    if (playerDataManager != null) {
      playerDataManager.save();
    }
  }
}
