package org.esoteric.minecraft.plugins.template.http.server.event.listeners;

import java.net.URI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import org.esoteric.minecraft.plugins.template.TemplatePaperPlugin;
import org.esoteric.minecraft.plugins.template.file.FileUtil;
import org.esoteric.minecraft.plugins.template.http.server.HttpServerManager;
import net.kyori.adventure.resource.ResourcePackInfo;
import net.kyori.adventure.resource.ResourcePackRequest;
import org.jetbrains.annotations.NotNull;

public class PlayerJoinListener implements Listener {

  private ResourcePackInfo resourcePackInfo;

  public PlayerJoinListener(@NotNull TemplatePaperPlugin plugin, @NotNull HttpServerManager httpServerManager) {
    resourcePackInfo = ResourcePackInfo.resourcePackInfo().hash(FileUtil.getSha1HexString(plugin.getResourcePackManager().getResourcePackZipFile()))
        .uri(URI.create("http://" + httpServerManager.getSocketAddress() + "/")).build();
  }

  @EventHandler
  public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
    Player player = event.getPlayer();
    player.sendResourcePacks(ResourcePackRequest.resourcePackRequest().packs(resourcePackInfo).required(true).build());
  }
}
