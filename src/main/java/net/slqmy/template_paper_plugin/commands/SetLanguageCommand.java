package net.slqmy.template_paper_plugin.commands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.GreedyStringArgument;

import java.util.stream.Stream;

import net.slqmy.template_paper_plugin.TemplatePaperPlugin;
import net.slqmy.template_paper_plugin.data.player.PlayerDataManager;
import net.slqmy.template_paper_plugin.data.player.PlayerProfile;
import net.slqmy.template_paper_plugin.language.LanguageData;
import net.slqmy.template_paper_plugin.language.LanguageManager;
import net.slqmy.template_paper_plugin.language.Message;

public class SetLanguageCommand extends CommandAPICommand {

  public SetLanguageCommand(TemplatePaperPlugin plugin) {
    super("set-language");

    LanguageManager languageManager = plugin.getLanguageManager();
    PlayerDataManager playerDataManager = plugin.getPlayerDataManager();

    String languageArgumentNodeName = "language";

    withArguments(new GreedyStringArgument(languageArgumentNodeName).includeSuggestions(ArgumentSuggestions.strings(Stream.of(languageManager.getLanguages().toArray(String[]::new)).map((language) -> {
      LanguageData data = languageManager.getLanguageData(language);

      return data.getName();
    }).toArray(String[]::new))));

    executesPlayer((player, arguments) -> {
      String selectedLanguage = (String) arguments.get(languageArgumentNodeName);

      String language = languageManager.getLanguageByName(selectedLanguage);

      PlayerProfile profile = playerDataManager.getPlayerProfile(player);
      profile.setLanguage(language);

      player.sendMessage(languageManager.getMessage(Message.SET_LANGUAGE_SUCCESSFULLY, player, selectedLanguage));
    });

    register(plugin);
  }
}
