package org.esotericorganisation.template_paper_plugin.language;

import java.util.function.BooleanSupplier;

import org.esotericorganisation.template_paper_plugin.custom_item.CustomItem;
import org.esotericorganisation.template_paper_plugin.custom_multiblock.CustomMultiblock;
import org.esotericorganisation.template_paper_plugin.custom_multientity.CustomMultientity;

public enum Message {
  SET_LANGUAGE_SUCCESSFULLY(() -> false),
  UNKNOWN_LANGUAGE(() -> false),

  UNKNOWN_CUSTOM_ITEM(CustomItem::isEnabled),
  UNKNOWN_CUSTOM_MULTIENTITY(CustomMultientity::isEnabled),
  UNKNOWN_CUSTOM_MULTIBLOCK(CustomMultiblock::isEnabled);

  private final BooleanSupplier isMessageInUseSupplier;

  public boolean isMessageInUse() {
    return isMessageInUseSupplier.getAsBoolean();
  }

  Message(BooleanSupplier isMessageInUseSupplier) {
    this.isMessageInUseSupplier = isMessageInUseSupplier;
  }

  Message() {
    this(() -> true);
  }

  public static boolean isEnabled() {
    for (Message message : values()) {
      if (message.isMessageInUse()) {
        return true;
      }
    }

    return false;
  }
}
