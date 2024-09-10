package dev.esoteric_development.example_paper_plugin.custom_multiblock;

public enum CustomMultiblock {
  ;

  public static boolean isEnabled() {
    return values().length != 0;
  }
}
