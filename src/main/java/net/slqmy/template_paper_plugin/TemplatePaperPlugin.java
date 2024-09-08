package net.slqmy.template_paper_plugin;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

import net.slqmy.template_paper_plugin.language.LanguageManager;

@DefaultQualifier(NonNull.class)
public final class TemplatePaperPlugin extends JavaPlugin implements Listener {

  private LanguageManager languageManager;

  @Override
  public void onEnable() {
    languageManager = new LanguageManager(this);
  }
}
