package org.esoteric_organisation.template_paper_plugin.file;

import java.io.File;
import java.io.IOException;

import org.esoteric_organisation.template_paper_plugin.TemplatePaperPlugin;

public class FileManager {

  private final ExamplePaperPlugin plugin;

  public FileManager(ExamplePaperPlugin plugin) {
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
