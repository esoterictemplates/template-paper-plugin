package org.esoteric.minecraft.plugins.template.resourcepack;

import foundation.esoteric.utility.file.FileUtility;
import org.apache.commons.io.FileUtils;
import org.esoteric.minecraft.plugins.template.PaperTemplatePlugin;
import org.esoteric.minecraft.plugins.template.file.FileUtil;

import java.io.File;
import java.util.List;

public class ResourcePackManager {

  private final PaperTemplatePlugin plugin;

  private final String resourcePackResourceFolderName = String.join(" ", PaperTemplatePlugin.class.getSimpleName().split("(?=[A-Z])")) + " Resource Pack";

  private final String resourcePackFileType = "application";
  private final String resourcePackFileExtension = "zip";
  private final String resourcePackFileMimeType = resourcePackFileType + FileUtil.getFileMimeTypeTypeSubtypeSeparator() + resourcePackFileExtension;

  private final String resourcePackAssetsFolderName = "assets";

  private String resourcePackZipFilePath;
  private File resourcePackZipFile;

  public String getResourcePackFileType() {
    return resourcePackFileType;
  }

  public String getResourcePackFileExtension() {
    return resourcePackFileExtension;
  }

  public String getResourcePackFileMimeType() {
    return resourcePackFileMimeType;
  }

  public String getResourcePackResourceFolderName() {
    return resourcePackResourceFolderName;
  }

  public String getResourceZipFilePath() {
    return resourcePackZipFilePath;
  }

  public File getResourcePackZipFile() {
    return resourcePackZipFile;
  }

  public ResourcePackManager(PaperTemplatePlugin plugin) {
    this.plugin = plugin;

    saveResourcepackZipFile();
  }

  private void saveResourcepackZipFile() {
    File resourcePackFolder = plugin.getFileManager().saveResourceFolder(resourcePackResourceFolderName, true);

    String[] resourcePackFiles = resourcePackFolder.list();
    if (resourcePackFiles == null || resourcePackFiles.length == 0) {
      return;
    }

    if (!List.of(resourcePackFiles).contains(resourcePackAssetsFolderName)) {
      return;
    }

    File assetsFile = new File(plugin.getDataPath() + File.separator + resourcePackResourceFolderName + File.separator + resourcePackAssetsFolderName);
    if (FileUtility.Companion.isRecursivelyEmpty(assetsFile)) {
      return;
    }

    resourcePackZipFilePath = plugin.getDataPath() + File.separator + resourcePackResourceFolderName + FileUtil.getFileExtensionSeparator() + resourcePackFileExtension;

    try {
      resourcePackZipFile = new File(resourcePackZipFilePath);
      FileUtil.zipFolder(resourcePackFolder, resourcePackZipFile);

      FileUtils.deleteDirectory(resourcePackFolder);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}
