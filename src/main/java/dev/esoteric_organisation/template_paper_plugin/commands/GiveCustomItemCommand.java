package dev.esoteric_organisation.template_paper_plugin.commands;

import java.util.stream.Stream;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.CustomArgument;
import dev.jorel.commandapi.arguments.CustomArgument.CustomArgumentException;
import dev.esoteric_organisation.template_paper_plugin.TemplatePaperPlugin;
import dev.esoteric_organisation.template_paper_plugin.custom_item.CustomItem;
import dev.esoteric_organisation.template_paper_plugin.language.Message;
import dev.jorel.commandapi.arguments.StringArgument;

public class GiveCustomItemCommand extends CommandAPICommand {

  public GiveCustomItemCommand(TemplatePaperPlugin plugin) {
    super("give-custom-item");

    String customItemArgumentNodeName = "custom-item-id";

    String[] customItemIds = Stream.of(CustomItem.values()).map(Enum::name).toArray(String[]::new);

    Argument<CustomItem> customItemArgument = new CustomArgument<>(new StringArgument(customItemArgumentNodeName), (info) -> {
      String input = info.currentInput();

      try {
        return CustomItem.valueOf(input);
      } catch (IllegalArgumentException exception) {
        throw CustomArgumentException.fromAdventureComponent(plugin.getLanguageManager().getMessage(Message.UNKNOWN_CUSTOM_ITEM, info.sender(), input));
      }
    }).includeSuggestions(ArgumentSuggestions.strings(customItemIds));

    executesPlayer((info) -> {
      CustomItem item = (CustomItem) info.args().get(customItemArgumentNodeName);

      plugin.getCustomItemManager().giveCustomItem(item, info.sender());
    });

    withPermission(CommandPermission.OP);
    withArguments(customItemArgument);

    register(plugin);
  }
}
