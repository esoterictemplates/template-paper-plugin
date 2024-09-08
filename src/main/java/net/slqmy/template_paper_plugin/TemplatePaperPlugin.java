package net.slqmy.template_paper_plugin;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

import net.slqmy.template_paper_plugin.data.player.PlayerDataManager;
import net.slqmy.template_paper_plugin.language.LanguageManager;

@DefaultQualifier(NonNull.class)
public final class TemplatePaperPlugin extends JavaPlugin implements Listener {

  private PlayerDataManager playerDataManager;
  private LanguageManager languageManager;

  @Override
  public void onEnable() {
    playerDataManager = new PlayerDataManager(this);
    languageManager = new LanguageManager(this);
  }
}
