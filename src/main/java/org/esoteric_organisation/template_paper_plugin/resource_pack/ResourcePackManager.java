package org.esoteric_organisation.template_paper_plugin.resource_pack;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;

import org.esoteric_organisation.template_paper_plugin.TemplatePaperPlugin;
import org.esoteric_organisation.template_paper_plugin.file.FileUtil;

public class ResourcePackManager {

  private final ExamplePaperPlugin plugin;

  private final String resourcePackResourceFolderName = String.join(" ", ExamplePaperPlugin.class.getSimpleName().split("(?=[A-Z])")) + " Resource Pack";

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

  public ResourcePackManager(ExamplePaperPlugin plugin) {
    this.plugin = plugin;

    saveResourcepackZipFile();
  }

  private void saveResourcepackZipFile() {
    assert plugin.getFileManager() != null;
    File resourcePackFolder = plugin.getFileManager().saveResourceFileFolder(resourcePackResourceFolderName);

    String[] resourcePackFiles = resourcePackFolder.list();
    if (resourcePackFiles == null || resourcePackFiles.length == 0) {
      return;
    }

    if (!List.of(resourcePackFiles).contains(resourcePackAssetsFolderName)) {
      return;
    }

    File assetsFile = new File(plugin.getDataPath() + File.separator + resourcePackResourceFolderName + File.separator + resourcePackAssetsFolderName);
    if (FileUtil.isDirectoryRecursivelyEmpty(assetsFile)) {
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
