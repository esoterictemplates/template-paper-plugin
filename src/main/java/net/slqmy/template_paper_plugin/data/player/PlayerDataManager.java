package net.slqmy.template_paper_plugin.data.player;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
      UUID playerUuid = UUID.fromString(fileName);

      FileReader reader;
      PlayerProfile profile;

      try {
        reader =  new FileReader(playerDataFile);

        profile = gson.fromJson(reader, PlayerProfile.class);

        reader.close();  
      } catch (IOException exception) {
        exception.printStackTrace();
        continue;
      }

      playerData.put(playerUuid, profile);
    }
  }

  public PlayerProfile getPlayerProfile(UUID uuid) {
    return playerData.get(uuid);
  }

  public PlayerProfile getPlayerProfile(Player player) {
    return playerData.get(player.getUniqueId());
  }
}
