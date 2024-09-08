package net.slqmy.template_paper_plugin.resource_pack;

import java.io.File;

import org.apache.commons.io.FileUtils;

import net.slqmy.template_paper_plugin.TemplatePaperPlugin;
import net.slqmy.template_paper_plugin.file.FileUtil;

public class ResourcePackManager {

  private final TemplatePaperPlugin plugin;

  private final String resourcePackResourceFolderName = String.join(" ", TemplatePaperPlugin.class.getSimpleName().split("(?=[A-Z])")) + " Resource Pack";

  private final String resourcePackFileType = "application";
  private final String resourcePackFileExtension = "zip";
  private final String resourcePackFileMimeType = resourcePackFileType + FileUtil.getFileMimeTypeTypeSubtypeSeparator() + resourcePackFileExtension;

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

  public ResourcePackManager(TemplatePaperPlugin plugin) {
    this.plugin = plugin;

    saveResourcepackZipFile();
  }

  private void saveResourcepackZipFile() {
    File resourcePackFolder = plugin.getFileManager().saveResourceFileFolder(resourcePackResourceFolderName);
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
