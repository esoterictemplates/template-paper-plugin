package net.slqmy.template_paper_plugin.commands;

import java.util.stream.Stream;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.CustomArgument;
import dev.jorel.commandapi.arguments.CustomArgument.CustomArgumentException;
import dev.jorel.commandapi.arguments.StringArgument;
import net.slqmy.template_paper_plugin.TemplatePaperPlugin;
import net.slqmy.template_paper_plugin.custom_multientity.CustomMultientity;
import net.slqmy.template_paper_plugin.language.Message;

public class SpawnCustomMultientityCommand extends CommandAPICommand {

  public SpawnCustomMultientityCommand(TemplatePaperPlugin plugin) {
    super("spawn");

    String customEntityArgumentNodeName = "custom-entity-id";

    String[] customEntityIds = Stream.of(CustomMultientity.values()).map((customEntity) -> customEntity.name()).toArray(String[]::new);

    Argument<CustomMultientity> customEntityArgument = new CustomArgument<>(new StringArgument(customEntityArgumentNodeName), (info) -> {
      String input = info.currentInput();

      try {
        return CustomMultientity.valueOf(input);
      } catch (IllegalArgumentException exception) {
        throw CustomArgumentException.fromAdventureComponent(plugin.getLanguageManager().getMessage(Message.UNKNOWN_CUSTOM_ENTITY, info.sender(), input));
      }
    }).includeSuggestions(ArgumentSuggestions.strings(customEntityIds));

    executesPlayer((info) -> {
      CustomMultientity entity = (CustomMultientity) info.args().get(customEntityArgumentNodeName);

      plugin.getCustomMultientityManager().spawnEntity(entity, info.sender().getLocation());
    });

    withPermission(CommandPermission.OP);
    withArguments(customEntityArgument);

    register(plugin);
  }
}
