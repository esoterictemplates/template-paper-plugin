package org.esoteric.minecraft.plugins.template.custom.multiblocks;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;

import com.google.gson.Gson;

import org.esoteric.minecraft.plugins.template.TemplatePaperPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

public class CustomMultiblockManager {

  private final TemplatePaperPlugin plugin;

  private final String multiblocksFileExtension = ".json";
  private final String multiblocksResourceFilePath = "data" + File.separator + "multiblocks" + multiblocksFileExtension;
  private final String multiblocksFilePath;
  private final File multiblocksFile;

  private final Map<CustomMultiblock, AbstractCustomMultiblock> customMultiblockMap = new HashMap<>();

  public CustomMultiblockManager(TemplatePaperPlugin plugin) {
    this.plugin = plugin;

    multiblocksFilePath = plugin.getDataFolder() + File.separator + multiblocksResourceFilePath;
    multiblocksFile = new File(multiblocksFilePath);

    load();
  }

  public void addCustomMultiblock(CustomMultiblock multiblockId, AbstractCustomMultiblock abstractCustomMultiblock) {
    customMultiblockMap.put(multiblockId, abstractCustomMultiblock);
  }

  public AbstractCustomMultiblock getAbstractCustomMultiblock(CustomMultiblock multiblockId) {
    return customMultiblockMap.get(multiblockId);
  }

  public void placeCustomMultiblock(CustomMultiblock multiblockId, Location location) {
    customMultiblockMap.get(multiblockId).getCustomMultiblock(location);
  }

  public List<StoredCustomMultiblock> getAllMultiblocks() {
    List<StoredCustomMultiblock> allMultiblocks = new ArrayList<>();

    for (Entry<CustomMultiblock, AbstractCustomMultiblock> multiblockInfo : customMultiblockMap.entrySet()) {
      allMultiblocks.add(new StoredCustomMultiblock(multiblockInfo));
    }

    return allMultiblocks;
  }

  private void load() {
    if (!multiblocksFile.exists()) {
      return;
    }

    StoredCustomMultiblocks multiblocks;

    Gson gson = new Gson();
    try {
      Reader reader = new FileReader(multiblocksFile);

      multiblocks = gson.fromJson(reader, StoredCustomMultiblocks.class);

      reader.close();
    } catch (IOException exception) {
      exception.printStackTrace();
      return;
    }

    loadStoredCustomMultiblocks(multiblocks);
  }

  public void save() {
    StoredCustomMultiblocks dataToSave = getStoredCustomMultiblocks();

    if (dataToSave.getStoredCustomMultiblocks().isEmpty()) {
      return;
    }

    if (!multiblocksFile.exists()) {
      plugin.saveResource(multiblocksResourceFilePath, false);
    }

    Gson gson = new Gson();

    try {
      Writer writer = new FileWriter(multiblocksFile);

      String json = gson.toJson(dataToSave);

      writer.write(json);

      writer.flush();
      writer.close();
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  public void loadStoredCustomMultiblocks(@NonNull StoredCustomMultiblocks multiblocks) {
    for (StoredCustomMultiblock multiblock : multiblocks.getStoredCustomMultiblocks()) {
      customMultiblockMap.get(multiblock.getMultiblockId()).addMultiblocks(multiblock.getBlockLocations());
    }
  }

  public StoredCustomMultiblocks getStoredCustomMultiblocks() {
    StoredCustomMultiblocks output = new StoredCustomMultiblocks();

    for (Entry<CustomMultiblock, AbstractCustomMultiblock> multiblockInfo : customMultiblockMap.entrySet()) {
      output.addCustomMultiblock(new StoredCustomMultiblock(multiblockInfo));
    }

    return output;
  }
}
