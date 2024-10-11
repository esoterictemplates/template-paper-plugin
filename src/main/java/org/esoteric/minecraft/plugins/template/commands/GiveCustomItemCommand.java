package org.esoteric.minecraft.plugins.template.commands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.CustomArgument;
import dev.jorel.commandapi.arguments.CustomArgument.CustomArgumentException;
import dev.jorel.commandapi.arguments.StringArgument;
import org.esoteric.minecraft.plugins.template.PaperTemplatePlugin;
import org.esoteric.minecraft.plugins.template.custom.items.CustomItem;
import org.esoteric.minecraft.plugins.template.language.Message;

import java.util.stream.Stream;

public class GiveCustomItemCommand extends CommandAPICommand {

  public GiveCustomItemCommand(PaperTemplatePlugin plugin) {
    super("give-custom-item");

    String customItemArgumentNodeName = "custom-item-id";

    String[] customItemIds = Stream.of(CustomItem.values()).map(Enum::name).toArray(String[]::new);

    Argument<CustomItem> customItemArgument = new CustomArgument<>(new StringArgument(customItemArgumentNodeName), (info) -> {
      String input = info.currentInput();

      try {
        return CustomItem.valueOf(input);
      } catch (IllegalArgumentException exception) {
        assert plugin.getLanguageManager() != null;
        throw CustomArgumentException.fromAdventureComponent(plugin.getLanguageManager().getMessage(Message.UNKNOWN_CUSTOM_ITEM, info.sender(), input));
      }
    }).includeSuggestions(ArgumentSuggestions.strings(customItemIds));

    executesPlayer((info) -> {
      CustomItem item = (CustomItem) info.args().get(customItemArgumentNodeName);

      assert plugin.getCustomItemManager() != null;
      plugin.getCustomItemManager().giveCustomItem(item, info.sender());
    });

    withPermission(CommandPermission.OP);
    withArguments(customItemArgument);

    register(plugin);
  }
}
