package foundation.esoteric.minecraft.plugins.template;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import foundation.esoteric.minecraft.plugins.library.commands.GiveCustomItemCommand;
import foundation.esoteric.minecraft.plugins.library.entity.CustomEntityManager;
import foundation.esoteric.minecraft.plugins.library.file.FileManagedPlugin;
import foundation.esoteric.minecraft.plugins.library.file.FileManager;
import foundation.esoteric.minecraft.plugins.library.http.server.HttpServerManager;
import foundation.esoteric.minecraft.plugins.library.item.CustomItemManager;
import foundation.esoteric.minecraft.plugins.library.item.CustomItemPlugin;
import foundation.esoteric.minecraft.plugins.library.resourcepack.ResourcePackManager;
import foundation.esoteric.minecraft.plugins.library.resourcepack.ResourcePackPlugin;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import foundation.esoteric.minecraft.plugins.template.commands.PlaceCustomMultiblockCommand;
import foundation.esoteric.minecraft.plugins.template.commands.SetLanguageCommand;
import foundation.esoteric.minecraft.plugins.template.custom.multiblocks.CustomMultiblock;
import foundation.esoteric.minecraft.plugins.template.custom.multiblocks.CustomMultiblockManager;
import foundation.esoteric.minecraft.plugins.template.data.player.PlayerDataManager;
import foundation.esoteric.minecraft.plugins.template.language.LanguageManager;
import foundation.esoteric.minecraft.plugins.template.language.Message;

public final class PaperTemplatePlugin extends JavaPlugin implements FileManagedPlugin, CustomItemPlugin, ResourcePackPlugin {

  private FileManager fileManager;
  private PlayerDataManager playerDataManager;
  private LanguageManager languageManager;
  private ResourcePackManager resourcePackManager;
  private HttpServerManager httpServerManager;
  private CustomItemManager customItemManager;
  private CustomEntityManager customMultientityManager;
  private CustomMultiblockManager customMultiblockManager;

  private final NamespacedKey customItemIdKey = new NamespacedKey(this, "custom_item_id");
  private final NamespacedKey customEntityIdKey = new NamespacedKey(this, "custom_entity_id");

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

  public CustomEntityManager getCustomMultientityManager() {
    return customMultientityManager;
  }

  public CustomMultiblockManager getCustomMultiblockManager() {
    return customMultiblockManager;
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

    fileManager = new FileManager(this);
    playerDataManager = new PlayerDataManager(this);
    if (Message.isEnabled()) {
      languageManager = new LanguageManager(this);
    }

    resourcePackManager = new ResourcePackManager(this);
    httpServerManager = new HttpServerManager(this);
    customItemManager = new CustomItemManager(this);
    customMultientityManager = new CustomEntityManager(this);
    if (CustomMultiblock.isEnabled()) {
      customMultiblockManager = new CustomMultiblockManager(this);
    }

    if (Message.isEnabled()) {
      new SetLanguageCommand(this);
    }
    new GiveCustomItemCommand(this);
    if (CustomMultiblock.isEnabled()) {
      new PlaceCustomMultiblockCommand(this);
    }
  }

  @Override
  public void onDisable() {
    if (playerDataManager != null) {
      playerDataManager.save();
    }
    if (customMultiblockManager != null) {
      customMultiblockManager.save();
    }
  }
}
