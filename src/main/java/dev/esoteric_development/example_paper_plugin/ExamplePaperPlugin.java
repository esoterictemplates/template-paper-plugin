package dev.esoteric_development.example_paper_plugin;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.framework.qual.DefaultQualifier;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import dev.esoteric_development.example_paper_plugin.commands.GiveCustomItemCommand;
import dev.esoteric_development.example_paper_plugin.commands.PlaceCustomMultiblockCommand;
import dev.esoteric_development.example_paper_plugin.commands.SetLanguageCommand;
import dev.esoteric_development.example_paper_plugin.commands.SpawnCustomMultientityCommand;
import dev.esoteric_development.example_paper_plugin.custom_item.CustomItem;
import dev.esoteric_development.example_paper_plugin.custom_item.CustomItemManager;
import dev.esoteric_development.example_paper_plugin.custom_multiblock.CustomMultiblock;
import dev.esoteric_development.example_paper_plugin.custom_multiblock.CustomMultiblockManager;
import dev.esoteric_development.example_paper_plugin.custom_multientity.CustomMultientity;
import dev.esoteric_development.example_paper_plugin.custom_multientity.CustomMultientityManager;
import dev.esoteric_development.example_paper_plugin.data.player.PlayerDataManager;
import dev.esoteric_development.example_paper_plugin.file.FileManager;
import dev.esoteric_development.example_paper_plugin.http_server.HttpServerManager;
import dev.esoteric_development.example_paper_plugin.language.LanguageManager;
import dev.esoteric_development.example_paper_plugin.language.Message;
import dev.esoteric_development.example_paper_plugin.resource_pack.ResourcePackManager;

@DefaultQualifier(NonNull.class)
public final class ExamplePaperPlugin extends JavaPlugin {

  @Nullable
  private FileManager fileManager;
  @Nullable
  private PlayerDataManager playerDataManager;
  @Nullable
  private LanguageManager languageManager;
  @Nullable
  private ResourcePackManager resourcePackManager;
  @Nullable
  private HttpServerManager httpServerManager;
  @Nullable
  private CustomItemManager customItemManager;
  @Nullable
  private CustomMultientityManager customMultientityManager;
  @Nullable
  private CustomMultiblockManager customMultiblockManager;

  private final NamespacedKey customItemIdKey = new NamespacedKey(this, "custom_item_id");
  private final NamespacedKey customEntityIdKey = new NamespacedKey(this, "custom_entity_id");

  @Nullable
  public FileManager getFileManager() {
    return fileManager;
  }

  @Nullable
  public PlayerDataManager getPlayerDataManager() {
    return playerDataManager;
  }

  @Nullable
  public LanguageManager getLanguageManager() {
    return languageManager;
  }

  @Nullable
  public ResourcePackManager getResourcePackManager() {
    return resourcePackManager;
  }

  @Nullable
  public HttpServerManager getHttpServerManager() {
    return httpServerManager;
  }

  @Nullable
  public CustomItemManager getCustomItemManager() {
    return customItemManager;
  }

  @Nullable
  public CustomMultientityManager getCustomMultientityManager() {
    return customMultientityManager;
  }

  @Nullable
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
