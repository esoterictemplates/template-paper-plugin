package net.slqmy.template_paper_plugin.data.player;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.google.gson.Gson;

import net.slqmy.template_paper_plugin.TemplatePaperPlugin;

public class PlayerDataManager {

  private final String playerDataFolderName = "player-data";
  private final String playerDataFolderPath;

  private final File playerDataFolder;

  private Map<UUID, PlayerProfile> playerData;

  public PlayerDataManager(TemplatePaperPlugin plugin) {
    playerDataFolderPath = plugin.getDataFolder().getPath() + File.separator + playerDataFolderName;
    playerDataFolder = new File(playerDataFolderPath);

    load();
  }

  private void load() {
    Gson gson = new Gson();

    File[] playerDataFiles = playerDataFolder.listFiles();
    for (File playerDataFile : playerDataFiles) {
      String fileName = playerDataFile.getName();
      UUID playerUuid = UUID.fromString(fileName);

      FileReader reader;
      PlayerProfile profile;

      try {
        reader = new FileReader(playerDataFile);

        profile = gson.fromJson(reader, PlayerProfile.class);

        reader.close();
      } catch (IOException exception) {
        exception.printStackTrace();
        continue;
      }

      playerData.put(playerUuid, profile);
    }
  }

  public void save() {
    Gson gson = new Gson();

    for (Entry<UUID, PlayerProfile> entry : playerData.entrySet()) {
      UUID uuid = entry.getKey();
      PlayerProfile profile = entry.getValue();

      File file = new File(playerDataFolderPath + File.separator + uuid.toString());

      FileWriter writer;

      try {
        file.createNewFile();

        writer = new FileWriter(file);

        String json = gson.toJson(profile);

        writer.write(json);

        writer.close();
        writer.flush();
      } catch (IOException exception) {
        exception.printStackTrace();
      }
    }
  }

  public PlayerProfile getPlayerProfile(UUID uuid) {
    return playerData.get(uuid);
  }

  public PlayerProfile getPlayerProfile(Player player) {
    return playerData.get(player.getUniqueId());
  }
}
