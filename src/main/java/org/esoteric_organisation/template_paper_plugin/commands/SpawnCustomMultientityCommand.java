package org.esoteric_organisation.template_paper_plugin.commands;

import java.util.stream.Stream;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.CustomArgument;
import dev.jorel.commandapi.arguments.CustomArgument.CustomArgumentException;
import org.esoteric_organisation.template_paper_plugin.TemplatePaperPlugin;
import org.esoteric_organisation.template_paper_plugin.custom_multientity.CustomMultientity;
import org.esoteric_organisation.template_paper_plugin.language.Message;
import dev.jorel.commandapi.arguments.StringArgument;

public class SpawnCustomMultientityCommand extends CommandAPICommand {

  public SpawnCustomMultientityCommand(ExamplePaperPlugin plugin) {
    super("spawn-custom-multientity");

    String customMultientityArgumentNodeName = "custom-multientity-id";

    String[] customMultientityIds = Stream.of(CustomMultientity.values()).map(Enum::name).toArray(String[]::new);

    Argument<CustomMultientity> customMultientityArgument = new CustomArgument<>(new StringArgument(customMultientityArgumentNodeName), (info) -> {
      String input = info.currentInput();

      try {
        return CustomMultientity.valueOf(input);
      } catch (IllegalArgumentException exception) {
        assert plugin.getLanguageManager() != null;
        throw CustomArgumentException.fromAdventureComponent(plugin.getLanguageManager().getMessage(Message.UNKNOWN_CUSTOM_MULTIENTITY, info.sender(), input));
      }
    }).includeSuggestions(ArgumentSuggestions.strings(customMultientityIds));

    executesPlayer((info) -> {
      CustomMultientity multientity = (CustomMultientity) info.args().get(customMultientityArgumentNodeName);

      assert plugin.getCustomMultientityManager() != null;
      plugin.getCustomMultientityManager().spawnEntity(multientity, info.sender().getLocation());
    });

    withPermission(CommandPermission.OP);
    withArguments(customMultientityArgument);

    register(plugin);
  }
}
