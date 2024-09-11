package dev.esoteric_organisation.example_paper_plugin.http_server.event.listeners;

import java.net.URI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import dev.esoteric_organisation.example_paper_plugin.ExamplePaperPlugin;
import dev.esoteric_organisation.example_paper_plugin.file.FileUtil;
import dev.esoteric_organisation.example_paper_plugin.http_server.HttpServerManager;
import net.kyori.adventure.resource.ResourcePackInfo;
import net.kyori.adventure.resource.ResourcePackRequest;
import org.checkerframework.checker.nullness.qual.NonNull;

public class PlayerJoinListener implements Listener {

  private ResourcePackInfo resourcePackInfo;

  public PlayerJoinListener(ExamplePaperPlugin plugin, HttpServerManager httpServerManager) {
    resourcePackInfo = ResourcePackInfo.resourcePackInfo().hash(FileUtil.getSha1HexString(plugin.getResourcePackManager().getResourcePackZipFile()))
        .uri(URI.create("http://" + httpServerManager.getSocketAddress() + "/")).build();
  }

  @EventHandler
  public void onPlayerJoin(@NonNull PlayerJoinEvent event) {
    Player player = event.getPlayer();
    player.sendResourcePacks(ResourcePackRequest.resourcePackRequest().packs(resourcePackInfo).required(true).build());
  }
}
