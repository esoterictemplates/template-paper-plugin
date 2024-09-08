package net.slqmy.template_paper_plugin.resource_pack.event.listeners;

import java.net.URI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.kyori.adventure.resource.ResourcePackCallback;
import net.kyori.adventure.resource.ResourcePackInfo;
import net.kyori.adventure.resource.ResourcePackRequest;
import net.kyori.adventure.text.Component;
import net.slqmy.template_paper_plugin.TemplatePaperPlugin;
import net.slqmy.template_paper_plugin.file.FileUtil;
import net.slqmy.template_paper_plugin.resource_pack.ResourcePackManager;

public class PlayerJoinListener implements Listener {

  private ResourcePackInfo resourcePackInfo;

  public PlayerJoinListener(TemplatePaperPlugin plugin, ResourcePackManager resourcePackManager) {
    resourcePackInfo = ResourcePackInfo.resourcePackInfo().hash(FileUtil.createSha1Hex(resourcePackManager.getResourcePackZipFile())).uri(URI.create("http://localhost:8000/")).build();
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    player.sendResourcePacks(
        ResourcePackRequest.resourcePackRequest().packs(resourcePackInfo).prompt(Component.text("Please download the resource pack!")).callback(ResourcePackCallback.onTerminal((uuid, audience) -> {
          Bukkit.getLogger().info("SUCCESS");
        }, (uuid, audience) -> {
          Bukkit.getLogger().info("FAILURE");
        })).required(true).build());
  }
}
