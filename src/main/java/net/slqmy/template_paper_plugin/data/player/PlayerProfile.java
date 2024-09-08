package net.slqmy.template_paper_plugin.data.player;

import net.slqmy.template_paper_plugin.language.Language;

public class PlayerProfile {

  private Language language;

  public PlayerProfile(Language language) {
    this.language = language;
  }

  public Language getLanguage() {
    return language;
  }

  public void setLanguage(Language language) {
    this.language = language;
  }
}
