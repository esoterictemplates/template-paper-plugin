package net.slqmy.template_paper_plugin.commands;

import java.util.stream.Stream;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.CustomArgument;
import dev.jorel.commandapi.arguments.CustomArgument.CustomArgumentException;
import dev.jorel.commandapi.arguments.CustomArgument.CustomArgumentInfo;
import dev.jorel.commandapi.arguments.CustomArgument.CustomArgumentInfoParser;
import dev.jorel.commandapi.arguments.StringArgument;
import net.slqmy.template_paper_plugin.TemplatePaperPlugin;
import net.slqmy.template_paper_plugin.custom_entity.CustomEntity;
import net.slqmy.template_paper_plugin.language.Message;

public class SpawnCustomEntityCommand extends CommandAPICommand {

  public SpawnCustomEntityCommand(TemplatePaperPlugin plugin) {
    super("spawn");

    if (CustomEntity.values().length == 0) {
      return;
    }

    String customEntityArgumentNodeName = "custom-entity-id";

    String[] customEntityIds = Stream.of(CustomEntity.values()).map((customEntity) -> customEntity.name()).toArray(String[]::new);

    Argument<CustomEntity> customEntityArgument = new CustomArgument<CustomEntity, String>(new StringArgument(customEntityArgumentNodeName), new CustomArgumentInfoParser<>() {
      @Override
      public CustomEntity apply(CustomArgumentInfo<String> info) throws CustomArgumentException {
        String input = info.currentInput();

        try {
          return CustomEntity.valueOf(input);
        } catch (IllegalArgumentException exception) {
          throw CustomArgumentException.fromAdventureComponent(plugin.getLanguageManager().getMessage(Message.UNKNOWN_CUSTOM_ENTITY, info.sender(), input));
        }
      }
    }).includeSuggestions(ArgumentSuggestions.strings(customEntityIds));

    executesPlayer((info) -> {
      CustomEntity entity = (CustomEntity) info.args().get(customEntityArgumentNodeName);

      plugin.getCustomEntityManager().spawnEntity(entity, info.sender().getLocation());
    });

    withPermission(CommandPermission.OP);
    withArguments(customEntityArgument);

    register(plugin);
  }
}
