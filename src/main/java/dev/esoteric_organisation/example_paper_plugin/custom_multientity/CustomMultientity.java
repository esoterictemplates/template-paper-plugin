package dev.esoteric_organisation.example_paper_plugin.custom_multientity;

public enum CustomMultientity {
  ;

  public static boolean isEnabled() {
    return values().length != 0;
  }
}
