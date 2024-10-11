package org.esotericorganisation.template_paper_plugin.commands;

import java.util.stream.Stream;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.CustomArgument;
import dev.jorel.commandapi.arguments.CustomArgument.CustomArgumentException;
import org.esotericorganisation.template_paper_plugin.TemplatePaperPlugin;
import org.esotericorganisation.template_paper_plugin.custom_multiblock.CustomMultiblock;
import org.esotericorganisation.template_paper_plugin.language.Message;
import dev.jorel.commandapi.arguments.StringArgument;

public class PlaceCustomMultiblockCommand extends CommandAPICommand {

  public PlaceCustomMultiblockCommand(TemplatePaperPlugin plugin) {
    super("place-custom-multiblock");

    String customMultiblockArgumentNodeName = "custom-multiblock-id";

    String[] customMultiblockIds = Stream.of(CustomMultiblock.values()).map(Enum::name).toArray(String[]::new);

    Argument<CustomMultiblock> customMultiblockArgument = new CustomArgument<>(new StringArgument(customMultiblockArgumentNodeName), (info) -> {
      String input = info.currentInput();

      try {
        return CustomMultiblock.valueOf(input);
      } catch (IllegalArgumentException exception) {
        assert plugin.getLanguageManager() != null;
        throw CustomArgumentException.fromAdventureComponent(plugin.getLanguageManager().getMessage(Message.UNKNOWN_CUSTOM_MULTIBLOCK, info.sender(), input));
      }
    }).includeSuggestions(ArgumentSuggestions.strings(customMultiblockIds));

    executesPlayer((info) -> {
      CustomMultiblock multiblock = (CustomMultiblock) info.args().get(customMultiblockArgumentNodeName);

      assert plugin.getCustomMultiblockManager() != null;
      plugin.getCustomMultiblockManager().placeCustomMultiblock(multiblock, info.sender().getLocation());
    });

    withPermission(CommandPermission.OP);
    withArguments(customMultiblockArgument);

    register(plugin);
  }
}
