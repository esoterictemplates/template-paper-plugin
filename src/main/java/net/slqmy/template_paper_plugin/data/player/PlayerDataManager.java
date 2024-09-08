package net.slqmy.template_paper_plugin.data.player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.google.gson.Gson;

import net.slqmy.template_paper_plugin.TemplatePaperPlugin;

public class PlayerDataManager {

  private Map<UUID, PlayerProfile> playerData;

  public PlayerDataManager(TemplatePaperPlugin plugin) {
    String playerDataFolderName = "player-data";
    String playerDataFolderPath = plugin.getDataFolder().getPath() + File.pathSeparator + playerDataFolderName;
    File playerDataFolder = new File(playerDataFolderPath);

    Gson gson = new Gson();

    File[] playerDataFiles = playerDataFolder.listFiles();
    for (File playerDataFile : playerDataFiles) {
      String fileName = playerDataFile.getName();
      UUID playerUUID = UUID.fromString(fileName);

      FileReader reader;

      try {
        reader =  new FileReader(playerDataFile);
      } catch (FileNotFoundException exception) {
        exception.printStackTrace();
        continue;
      }

      PlayerProfile profile = gson.fromJson(reader, PlayerProfile.class);
      playerData.put(playerUUID, profile);
    }
  }

  public PlayerProfile getPlayerProfile(UUID uuid) {
    return playerData.get(uuid);
  }

  public PlayerProfile getPlayerProfile(Player player) {
    return playerData.get(player.getUniqueId());
  }
}
