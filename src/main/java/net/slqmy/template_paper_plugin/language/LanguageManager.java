package net.slqmy.template_paper_plugin.language;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.slqmy.template_paper_plugin.TemplatePaperPlugin;
import net.slqmy.template_paper_plugin.data.player.PlayerProfile;

import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class LanguageManager {

  private final MiniMessage miniMessage = MiniMessage.miniMessage();

  private final TemplatePaperPlugin plugin;

  private final Map<Language, LanguageData> languages = new HashMap<>();

  public LanguageManager(TemplatePaperPlugin plugin) {
    this.plugin = plugin;

    File dataFolder = plugin.getDataFolder();
    String languagesFolderName = "languages";
    String languagesFolderPath = dataFolder.getPath() + File.separator + languagesFolderName;

    plugin.saveResource(languagesFolderName, false);

    for (Language language : Language.values()) {
      String languageEnumName = language.name();

      String languageFolderPath = languagesFolderPath + File.separator + languageEnumName + File.separator;
      String languageFolderResourcePath = languagesFolderName + File.separator + languageEnumName + File.separator;

      String manifestFileName = "manifest.yaml";
      String messagesFileName = "messages.yaml";

      String manifestFilePath = languageFolderPath + manifestFileName;
      String messagesFilePath = languageFolderPath + messagesFileName;

      String manifestResourceFilePath = languageFolderResourcePath + manifestFileName;
      String messagesResourceFilePath = languageFolderResourcePath + messagesFileName;

      plugin.saveResource(manifestResourceFilePath, false);
      plugin.saveResource(messagesResourceFilePath, false);

      File manifestFile = new File(manifestFilePath);
      File messagesFile = new File(messagesFilePath);

      YamlConfiguration manifestConfiguration = YamlConfiguration.loadConfiguration(manifestFile);
      YamlConfiguration messagesConfiguration = YamlConfiguration.loadConfiguration(messagesFile);

      String languageName = manifestConfiguration.getString("name");

      Map<Message, String> messages = new HashMap<>();

      for (Message message : Message.values()) {
        String mappedResult = messagesConfiguration.getString(message.name());

        messages.put(message, mappedResult);
      }

      LanguageData languageData = new LanguageData(languageName, messages);
      languages.put(language, languageData);
    }
  }

  public Language getLanguageByName(String name) {
    for (Language language : Language.values()) {
      if (getLanguageData(language).getName().equals(name)) {
        return language;
      }
    }

    return null;
  }

  public LanguageData getLanguageData(Language language) {
    return languages.get(language);
  }

  public Component getMessage(Message message, Language language, Object... arguments) {
    return miniMessage.deserialize(String.format(languages.get(language).getMessages().get(message), arguments));
  }

  public Component getMessage(Message message, PlayerProfile playerProfile, Object... arguments) {
    Language selectedLanguage = playerProfile.getLanguage();

    return getMessage(message, selectedLanguage != null ? selectedLanguage : Language.valueOf(plugin.getConfig().getString("default-language")), arguments);
  }

  public Component getMessage(Message message, UUID playerUuid, Object... arguments) {
    return getMessage(message, plugin.getPlayerDataManager().getPlayerProfile(playerUuid), arguments);
  }

  public Component getMessage(Message message, Player player, Object... arguments) {
    return getMessage(message, player.getUniqueId(), arguments);
  }
}
