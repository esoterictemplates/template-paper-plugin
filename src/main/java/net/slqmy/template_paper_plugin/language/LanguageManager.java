package net.slqmy.template_paper_plugin.language;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.slqmy.template_paper_plugin.TemplatePaperPlugin;

import java.util.Map;
import java.util.HashMap;
import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

public class LanguageManager {

    private final TemplatePaperPlugin plugin;

    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    private final Map<Language, LanguageData> languages = new HashMap<>();

    public LanguageManager(TemplatePaperPlugin plugin) {
        this.plugin = plugin;

        File dataFolder = plugin.getDataFolder();
        String languagesFolderPath = dataFolder.getPath() + File.separator + "languages";

        for (Language language : Language.values()) {
            String languageFolderPath = languagesFolderPath + File.separator + language.getKebabCaseName() + File.separator;

            String manifestFilePath = languageFolderPath + "manifest.yaml";
            String messagesFilePath = languageFolderPath + "messages.yaml";

            File manifestFile = new File(manifestFilePath);
            File messagesFile = new File(messagesFilePath);

            YamlConfiguration manifestConfiguration = YamlConfiguration.loadConfiguration(manifestFile);
            YamlConfiguration messagesConfiguration = YamlConfiguration.loadConfiguration(messagesFile);

            String languageName = manifestConfiguration.getString("name");

            Map<Message, String> messages = new HashMap<>();

            for (Message message : Message.values()) {
                String mappedResult = messagesConfiguration.getString(message.getKebabCaseName());

                messages.put(message, mappedResult);
            }

            LanguageData languageData = new LanguageData(languageName, messages);
            languages.put(language, languageData);
        }
    }

    public Component getMessage(Message message, Language language, Object... arguments) {        
        return miniMessage.deserialize(String.format(languages.get(language).getMessages().get(message), arguments));
    }
}
