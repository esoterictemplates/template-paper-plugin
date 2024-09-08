package net.slqmy.template_paper_plugin.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.slqmy.template_paper_plugin.TemplatePaperPlugin;
import net.slqmy.template_paper_plugin.language.LanguageManager;

import java.util.Locale;

public class PlayerJoinListener implements Listener {
  
  private final TemplatePaperPlugin plugin;

  public PlayerJoinListener(TemplatePaperPlugin plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onPlayerConnect(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    Locale playerLocale = player.locale();

    LanguageManager languageManager = plugin.getLanguageManager();

    String playerLanguage = playerLocale.getDisplayName();
    if (languageManager.getLanguages().contains(playerLanguage)) {
      languageManager.setPlayerLanguage(player, playerLanguage);
    }
  }
}
