package foundation.esoteric.minecraft.plugins.template.language;

import java.util.function.BooleanSupplier;

public enum Message {
  SET_LANGUAGE_SUCCESSFULLY(() -> false),
  UNKNOWN_LANGUAGE(() -> false),

  UNKNOWN_CUSTOM_ITEM(),
  UNKNOWN_CUSTOM_MULTIENTITY(),
  UNKNOWN_CUSTOM_MULTIBLOCK();

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
