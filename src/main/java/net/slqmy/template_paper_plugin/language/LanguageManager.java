package net.slqmy.template_paper_plugin.language;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.slqmy.template_paper_plugin.TemplatePaperPlugin;
import net.slqmy.template_paper_plugin.data.player.PlayerProfile;
import net.slqmy.template_paper_plugin.util.FileUtil;

import java.util.UUID;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class LanguageManager {

  private final MiniMessage miniMessage = MiniMessage.miniMessage();

  private final TemplatePaperPlugin plugin;

  private final String languagesFolderName = "languages";
  private final String languagesFolderPath;
  private final File languagesFolder;

  private final String defaultLanguage;

  private final Map<String, Map<Message, String>> languages = new HashMap<>();

  public String getDefaultLanguage() {
    return defaultLanguage;
  }

  public Set<String> getLanguages() {
    return languages.keySet();
  }

  public LanguageManager(TemplatePaperPlugin plugin) {
    this.plugin = plugin;

    File dataFolder = plugin.getDataFolder();
    languagesFolderPath = dataFolder.getPath() + File.separator + languagesFolderName;
    languagesFolder = new File(languagesFolderPath);

    saveLanguageFiles();
    loadLanguageMessages();

    defaultLanguage = plugin.getConfig().getString("default-language");
  }

  private void saveLanguageFiles() {
    String languagesResourceFolderName = languagesFolderName + "/";
    List<String> languageResourceFileNames = FileUtil.getResourceFolderResourceFileNames(languagesResourceFolderName);

    languageResourceFileNames.forEach((fileName) -> plugin.saveResource(languagesFolderName + File.separator + fileName, false));
  }

  private void loadLanguageMessages() {
    for (File languageMessagesFile : languagesFolder.listFiles()) {
      String languageName = languageMessagesFile.getName().split("\\.", 2)[0];

      String languageMessagesResourcePath = languagesFolderName + File.separator + languageName + ".yaml";
      plugin.saveResource(languageMessagesResourcePath, false);

      YamlConfiguration messagesConfiguration = YamlConfiguration.loadConfiguration(languageMessagesFile);
      Map<Message, String> messages = new HashMap<>();

      for (Message message : Message.values()) {
        String mappedResult = messagesConfiguration.getString(message.name());

        if (mappedResult != null) {
          messages.put(message, mappedResult);
        }
      }

      languages.put(languageName, messages);
    }
  }

  public String getPlayerLanguage(PlayerProfile playerProfile) {
    return playerProfile.getLanguage();
  }

  public String getPlayerLanguage(UUID uuid) {
    return plugin.getPlayerDataManager().getPlayerProfile(uuid).getLanguage();
  }

  public String getPlayerLanguage(Player player) {
    return getPlayerLanguage(player.getUniqueId());
  }

  public String getLanguage(CommandSender commandSender) {
    if (commandSender instanceof Player player) {
      return getPlayerLanguage(player);
    } else {
      return defaultLanguage;
    }
  }

  public void setPlayerLanguage(PlayerProfile playerProfile, String language) {
    playerProfile.setLanguage(language);
  }

  public void setPlayerLanguage(UUID uuid, String language) {
    setPlayerLanguage(plugin.getPlayerDataManager().getPlayerProfile(uuid), language);
  }

  public void setPlayerLanguage(Player player, String language) {
    setPlayerLanguage(player.getUniqueId(), language);
  }

  private Component getMessage(Message message, String language, boolean fallbackOnDefaultLanguage, Object... arguments) {
    Map<Message, String> languageMessageMap = languages.get(language);
    String miniMessageString = languageMessageMap.get(message);

    if (miniMessageString == null) {
      return fallbackOnDefaultLanguage ? getMessage(message, defaultLanguage, false, arguments) : null;
    }

    return miniMessage.deserialize(String.format(miniMessageString, arguments));
  }

  private Component getMessage(Message message, String language, Object... arguments) {
    return getMessage(message, language, true, arguments);
  }

  public Component getMessage(Message message, CommandSender commandSender, boolean fallbackOnDefaultLanguage, Object... arguments) {
    String language = defaultLanguage;

    if (commandSender instanceof Player player) {
      PlayerProfile profile = plugin.getPlayerDataManager().getPlayerProfile(player, false);

      if (profile == null) {
        language = getPlayerLocale(player);
      } else {
        language = profile.getLanguage();

        if (language == null) {
          language = getPlayerLocale(player);
        }
      }

      if (language == null) {
        language = defaultLanguage;
      }
    }

    return getMessage(message, language, fallbackOnDefaultLanguage, arguments);
  }

  public Component getMessage(Message message, CommandSender commandSender, Object... arguments) {
    return getMessage(message, commandSender, true, arguments);
  }

  public Component getMessage(Message message, UUID uuid, boolean fallbackOnDefaultLanguage, Object... arguments) {
    String language = null;

    Player player = Bukkit.getPlayer(uuid);
    PlayerProfile profile = plugin.getPlayerDataManager().getPlayerProfile(uuid, false);

    if (profile != null) {
      language = profile.getLanguage();
    }

    if (language == null) {
      language = getPlayerLocale(player);
    }

    if (language == null) {
      language = defaultLanguage;
    }

    return getMessage(message, language, fallbackOnDefaultLanguage, arguments);
  }

  public Component getMessage(Message message, UUID uuid, Object... arguments) {
    return getMessage(message, uuid, true, arguments);
  }

  private String getPlayerLocale(Player player) {
    if (player == null) {
      return null;
    }

    Locale playerLocale = player.locale();
    String localeDisplayName = playerLocale.getDisplayName();

    if (!getLanguages().contains(localeDisplayName)) {
      return null;
    }

    return localeDisplayName;
  }
}
