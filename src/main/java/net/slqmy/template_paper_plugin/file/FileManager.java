package net.slqmy.template_paper_plugin.file;

import java.io.FileNotFoundException;
import java.io.IOException;

import net.slqmy.template_paper_plugin.TemplatePaperPlugin;

public class FileManager {

  private final TemplatePaperPlugin plugin;

  public FileManager(TemplatePaperPlugin plugin) {
    this.plugin = plugin;
  }

  public void saveResourceFileFolder(String resourceFileFolderPath, boolean shouldReplaceExistingFiles) {
    try {
      FileUtil.getResourceFileFolderResourceFilePathsRecursively(resourceFileFolderPath).forEach((resourceFilePath) -> {
        plugin.saveResource(resourceFilePath, shouldReplaceExistingFiles);
      });
    } catch (IOException exception) {
      if (!(exception instanceof FileNotFoundException)) {
        exception.printStackTrace();
      }
    }
  }

  public void saveResourceFileFolder(String resourceFileFolderPath) {
    saveResourceFileFolder(resourceFileFolderPath, true);
  }
}
