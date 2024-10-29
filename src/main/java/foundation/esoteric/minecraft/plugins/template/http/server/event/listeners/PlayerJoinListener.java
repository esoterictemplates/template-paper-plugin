package foundation.esoteric.minecraft.plugins.template.http.server.event.listeners;

import foundation.esoteric.utility.file.FileUtility;
import net.kyori.adventure.resource.ResourcePackInfo;
import net.kyori.adventure.resource.ResourcePackRequest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import foundation.esoteric.minecraft.plugins.template.PaperTemplatePlugin;
import foundation.esoteric.minecraft.plugins.template.http.server.HttpServerManager;
import org.jetbrains.annotations.NotNull;

import java.net.URI;

public class PlayerJoinListener implements Listener {

  private ResourcePackInfo resourcePackInfo;

  public PlayerJoinListener(@NotNull PaperTemplatePlugin plugin, @NotNull HttpServerManager httpServerManager) {
    resourcePackInfo = ResourcePackInfo.resourcePackInfo().hash(FileUtility.Companion.getSha1Hash(plugin.getResourcePackManager().getResourcePackZipFile()))
      .uri(URI.create("http://" + httpServerManager.getSocketAddress() + "/")).build();
  }

  @EventHandler
  public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
    Player player = event.getPlayer();
    player.sendResourcePacks(ResourcePackRequest.resourcePackRequest().packs(resourcePackInfo).required(true).build());
  }
}
