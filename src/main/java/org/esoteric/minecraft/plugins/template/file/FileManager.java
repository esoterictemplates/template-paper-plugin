package org.esoteric.minecraft.plugins.template.file;

import org.esoteric.minecraft.plugins.template.PaperTemplatePlugin;

import java.io.File;
import java.io.IOException;

public class FileManager {

  private final PaperTemplatePlugin plugin;

  public FileManager(PaperTemplatePlugin plugin) {
    this.plugin = plugin;
  }

  public File saveResourceFileFolder(String resourceFileFolderPath, boolean shouldReplaceExistingFiles) {
    try {
      FileUtil.getResourceFileFolderResourceFilePathsRecursively(resourceFileFolderPath).forEach((resourceFilePath) -> {
        plugin.saveResource(resourceFilePath, shouldReplaceExistingFiles);
      });
      return new File(plugin.getDataPath() + File.separator + resourceFileFolderPath);
    } catch (IOException exception) {
      exception.printStackTrace();
      return null;
    }
  }

  public File saveResourceFileFolder(String resourceFileFolderPath) {
    return saveResourceFileFolder(resourceFileFolderPath, true);
  }
}
