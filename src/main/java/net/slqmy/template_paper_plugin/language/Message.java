package net.slqmy.template_paper_plugin.language;

public enum Message {
  ;

  private final String kebabCaseName;

  public String getKebabCaseName() {
    return kebabCaseName;
  }

  Message() {
    kebabCaseName = this.name().toLowerCase().replace("_", "-");
  }
}
