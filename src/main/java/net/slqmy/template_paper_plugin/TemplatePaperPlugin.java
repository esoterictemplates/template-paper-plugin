package net.slqmy.template_paper_plugin;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import net.slqmy.template_paper_plugin.commands.GiveCustomItemCommand;
import net.slqmy.template_paper_plugin.commands.SetLanguageCommand;
import net.slqmy.template_paper_plugin.commands.SpawnCustomEntityCommand;
import net.slqmy.template_paper_plugin.custom_entity.CustomEntity;
import net.slqmy.template_paper_plugin.custom_entity.CustomEntityManager;
import net.slqmy.template_paper_plugin.custom_item.CustomItem;
import net.slqmy.template_paper_plugin.custom_item.CustomItemManager;
import net.slqmy.template_paper_plugin.data.player.PlayerDataManager;
import net.slqmy.template_paper_plugin.file.FileManager;
import net.slqmy.template_paper_plugin.http_server.HttpServerManager;
import net.slqmy.template_paper_plugin.language.LanguageManager;
import net.slqmy.template_paper_plugin.resource_pack.ResourcePackManager;

@DefaultQualifier(NonNull.class)
public final class TemplatePaperPlugin extends JavaPlugin {

  private FileManager fileManager;
  private PlayerDataManager playerDataManager;
  private LanguageManager languageManager;
  private ResourcePackManager resourcePackManager;
  private HttpServerManager httpServerManager;
  private CustomItemManager customItemManager;
  private CustomEntityManager customEntityManager;

  private final NamespacedKey customItemIdKey = new NamespacedKey(this, "custom_item_id");
  private final NamespacedKey customEntityIdKey = new NamespacedKey(this, "custom_entity_id");
  private final String customMultiblockIdKey = "custom_multiblock_id";

  public FileManager getFileManager() {
    return fileManager;
  }

  public PlayerDataManager getPlayerDataManager() {
    return playerDataManager;
  }

  public LanguageManager getLanguageManager() {
    return languageManager;
  }

  public ResourcePackManager getResourcePackManager() {
    return resourcePackManager;
  }

  public HttpServerManager getHttpServerManager() {
    return httpServerManager;
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

  public String getCustomMultiblockIdKey() {
    return customMultiblockIdKey;
  }

  @Override
  public void onEnable() {
    getDataFolder().mkdir();
    saveDefaultConfig();

    CommandAPIBukkitConfig commandAPIConfig = new CommandAPIBukkitConfig(this);

    CommandAPI.onLoad(commandAPIConfig);
    CommandAPI.onEnable();

    fileManager = new FileManager(this);
    playerDataManager = new PlayerDataManager(this);
    languageManager = new LanguageManager(this);
    resourcePackManager = new ResourcePackManager(this);
    httpServerManager = new HttpServerManager(this);
    customItemManager = new CustomItemManager(this);
    customEntityManager = new CustomEntityManager(this);

    new SetLanguageCommand(this);

    if (CustomItem.values().length != 0) {
      new GiveCustomItemCommand(this);
    }
    if (CustomEntity.values().length != 0) {
      new SpawnCustomEntityCommand(this);
    }
  }

  @Override
  public void onDisable() {
    if (playerDataManager != null) {
      playerDataManager.save();
    }
  }
}
