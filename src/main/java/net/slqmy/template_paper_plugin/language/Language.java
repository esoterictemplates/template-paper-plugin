package net.slqmy.template_paper_plugin.language;

public enum Language {
  EN_UK;

  private final String kebabCaseName;

  public String getKebabCaseName() {
    return kebabCaseName;
  }

  Language() {
    kebabCaseName = this.name().toLowerCase().replace("_", "-");
  }
}
