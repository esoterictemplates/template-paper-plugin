package net.slqmy.template_paper_plugin.language;

import java.util.Map;

public class LanguageData {

  private final Language name;
  private final Map<Message, String> messages;

  public Language getName() {
    return name;
  }

  public Map<Message, String> getMessages() {
    return messages;
  }

  public LanguageData(Language name, Map<Message, String> messages) {
    this.name = name;
    this.messages = messages;
  }
}
