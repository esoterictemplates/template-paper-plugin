package net.slqmy.template_paper_plugin.commands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.MultiLiteralArgument;

import java.util.stream.Stream;

import net.slqmy.template_paper_plugin.TemplatePaperPlugin;
import net.slqmy.template_paper_plugin.language.Language;
import net.slqmy.template_paper_plugin.language.LanguageData;

public class SetLanguageCommand extends CommandAPICommand {

  public SetLanguageCommand(TemplatePaperPlugin plugin) {
    super("set-language");

    withArguments(new MultiLiteralArgument("language", Stream.of(Language.values()).map((language) -> {
      LanguageData data = plugin.getLanguageManager().getLanguageData(language);

      return data.getName();
    }).toArray(String[]::new)));

    executes((sender, arguments) -> {
      
    });

    register(plugin);
  }
}
