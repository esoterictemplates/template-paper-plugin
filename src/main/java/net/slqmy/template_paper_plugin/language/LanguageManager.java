package net.slqmy.template_paper_plugin.language;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.slqmy.template_paper_plugin.TemplatePaperPlugin;
import net.slqmy.template_paper_plugin.data.player.PlayerProfile;

import java.util.UUID;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.io.File;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jline.utils.InputStreamReader;

public class LanguageManager {

  private final MiniMessage miniMessage = MiniMessage.miniMessage();

  private final TemplatePaperPlugin plugin;

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
    String languagesFolderName = "languages";
    String languagesFolderPath = dataFolder.getPath() + File.separator + languagesFolderName;
    File languagesFolder = new File(languagesFolderPath);

    String[] languageFileNames = {};

    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    try (InputStream inputStream = loader.getResourceAsStream(languagesFolderName);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

      languageFileNames = bufferedReader.lines().toArray(String[]::new);

      bufferedReader.lines().forEach(System.out::println);
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }

    for (String languageFileName : languageFileNames) {
      plugin.saveResource(languagesFolderName + File.separator + languageFileName, false);
    }

    for (File languageMessagesFile : languagesFolder.listFiles()) {
      String languageName = languageMessagesFile.getName();

      String languageMessagesResourcePath = languagesFolderName + File.separator + languageName;
      plugin.saveResource(languageMessagesResourcePath, false);

      YamlConfiguration messagesConfiguration = YamlConfiguration.loadConfiguration(languageMessagesFile);
      Map<Message, String> messages = new HashMap<>();

      for (Message message : Message.values()) {
        String mappedResult = messagesConfiguration.getString(message.name());

        messages.put(message, mappedResult);
      }

      languages.put(languageName, messages);
    }

    defaultLanguage = plugin.getConfig().getString("default-language");
  }

  public Component getMessage(Message message, String language, Object... arguments) {
    return miniMessage.deserialize(String.format(languages.get(language).get(message), arguments));
  }

  public Component getMessage(Message message, PlayerProfile playerProfile, Object... arguments) {
    String selectedLanguage = playerProfile.getLanguage();

    return getMessage(message, selectedLanguage != null ? selectedLanguage : defaultLanguage, arguments);
  }

  public Component getMessage(Message message, UUID playerUuid, Object... arguments) {
    return getMessage(message, plugin.getPlayerDataManager().getPlayerProfile(playerUuid), arguments);
  }

  public Component getMessage(Message message, Player player, Object... arguments) {
    return getMessage(message, player.getUniqueId(), arguments);
  }

  public Component getMessage(Message message, CommandSender commandSender, Object... arguments) {
    String language;

    if (commandSender instanceof Player player) {
      language = plugin.getPlayerDataManager().getPlayerProfile(player.getUniqueId()).getLanguage();
    } else {
      language = defaultLanguage;
    }

    return getMessage(message, language, arguments);
  }
}
