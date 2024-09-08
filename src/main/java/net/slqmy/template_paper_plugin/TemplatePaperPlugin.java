package net.slqmy.template_paper_plugin;

import java.io.File;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import net.slqmy.template_paper_plugin.commands.GiveCustomItemCommand;
import net.slqmy.template_paper_plugin.commands.SetLanguageCommand;
import net.slqmy.template_paper_plugin.commands.SpawnCustomEntityCommand;
import net.slqmy.template_paper_plugin.custom_entity.CustomEntityManager;
import net.slqmy.template_paper_plugin.custom_item.CustomItemManager;
import net.slqmy.template_paper_plugin.data.player.PlayerDataManager;
import net.slqmy.template_paper_plugin.file.FileManager;
import net.slqmy.template_paper_plugin.file.FileUtil;
import net.slqmy.template_paper_plugin.language.LanguageManager;

@DefaultQualifier(NonNull.class)
public final class TemplatePaperPlugin extends JavaPlugin {

  private final String resourcePackResourceFolderName = String.join(" ", getClass().getSimpleName().split("(?=[A-Z])")) + " Resource Pack";

  private FileManager fileManager;
  private PlayerDataManager playerDataManager;
  private LanguageManager languageManager;
  private CustomItemManager customItemManager;
  private CustomEntityManager customEntityManager;

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

    fileManager = new FileManager(this);
    playerDataManager = new PlayerDataManager(this);
    languageManager = new LanguageManager(this);
    customItemManager = new CustomItemManager(this);
    customEntityManager = new CustomEntityManager(this);

    new SetLanguageCommand(this);

    new GiveCustomItemCommand(this);
    new SpawnCustomEntityCommand(this);

    fileManager.saveResourceFileFolder(resourcePackResourceFolderName);

    try {
      FileUtil.zipFolder(Path.of(getDataPath() + File.separator + resourcePackResourceFolderName), Path.of(getDataPath() + File.separator + resourcePackResourceFolderName + ".zip"));
      File resourcePackFolder = new File(getDataPath() + File.separator + resourcePackResourceFolderName);
      FileUtils.deleteDirectory(resourcePackFolder);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  @Override
  public void onDisable() {
    if (playerDataManager != null) {
      playerDataManager.save();
    }
  }
}
