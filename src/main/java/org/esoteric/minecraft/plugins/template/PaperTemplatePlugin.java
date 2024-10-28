package org.esoteric.minecraft.plugins.template;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import foundation.esoteric.minecraft.plugins.library.file.FileManagedPlugin;
import foundation.esoteric.minecraft.plugins.library.file.FileManager;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.esoteric.minecraft.plugins.template.commands.GiveCustomItemCommand;
import org.esoteric.minecraft.plugins.template.commands.PlaceCustomMultiblockCommand;
import org.esoteric.minecraft.plugins.template.commands.SetLanguageCommand;
import org.esoteric.minecraft.plugins.template.commands.SpawnCustomMultientityCommand;
import org.esoteric.minecraft.plugins.template.custom.items.CustomItem;
import org.esoteric.minecraft.plugins.template.custom.items.CustomItemManager;
import org.esoteric.minecraft.plugins.template.custom.multiblocks.CustomMultiblock;
import org.esoteric.minecraft.plugins.template.custom.multiblocks.CustomMultiblockManager;
import org.esoteric.minecraft.plugins.template.custom.multientities.CustomMultientity;
import org.esoteric.minecraft.plugins.template.custom.multientities.CustomMultientityManager;
import org.esoteric.minecraft.plugins.template.data.player.PlayerDataManager;
import org.esoteric.minecraft.plugins.template.http.server.HttpServerManager;
import org.esoteric.minecraft.plugins.template.language.LanguageManager;
import org.esoteric.minecraft.plugins.template.language.Message;
import org.esoteric.minecraft.plugins.template.resourcepack.ResourcePackManager;

public final class PaperTemplatePlugin extends JavaPlugin implements FileManagedPlugin {

  private FileManager fileManager;
  private PlayerDataManager playerDataManager;
  private LanguageManager languageManager;
  private ResourcePackManager resourcePackManager;
  private HttpServerManager httpServerManager;
  private CustomItemManager customItemManager;
  private CustomMultientityManager customMultientityManager;
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

  public CustomMultientityManager getCustomMultientityManager() {
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
    if (CustomItem.isEnabled()) {
      customItemManager = new CustomItemManager(this);
    }
    if (CustomMultientity.isEnabled()) {
      customMultientityManager = new CustomMultientityManager(this);
    }
    if (CustomMultiblock.isEnabled()) {
      customMultiblockManager = new CustomMultiblockManager(this);
    }

    if (Message.isEnabled()) {
      new SetLanguageCommand(this);
    }
    if (CustomItem.isEnabled()) {
      new GiveCustomItemCommand(this);
    }
    if (CustomMultientity.isEnabled()) {
      new SpawnCustomMultientityCommand(this);
    }
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
