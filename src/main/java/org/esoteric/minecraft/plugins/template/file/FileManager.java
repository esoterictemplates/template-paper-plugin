package org.esoteric.minecraft.plugins.template.file;

import foundation.esoteric.utility.resource.ResourceUtility;
import org.esoteric.minecraft.plugins.template.PaperTemplatePlugin;

import java.io.File;

public class FileManager {

  private final PaperTemplatePlugin plugin;

  public FileManager(PaperTemplatePlugin plugin) {
    this.plugin = plugin;
  }

  public File saveResourceFileFolder(String resourceFileFolderPath, boolean shouldReplaceExistingFiles) {
    ResourceUtility.Companion.getResourceFilePaths(resourceFileFolderPath).forEach((resourceFilePath) -> plugin.saveResource(resourceFilePath.toString(), shouldReplaceExistingFiles));
    return new File(plugin.getDataPath() + File.separator + resourceFileFolderPath);
  }

  public File saveResourceFileFolder(String resourceFileFolderPath) {
    return saveResourceFileFolder(resourceFileFolderPath, true);
  }
}
