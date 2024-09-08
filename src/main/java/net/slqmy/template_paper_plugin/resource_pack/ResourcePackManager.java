package net.slqmy.template_paper_plugin.resource_pack;

import java.io.File;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;

import net.slqmy.template_paper_plugin.TemplatePaperPlugin;
import net.slqmy.template_paper_plugin.file.FileUtil;

public class ResourcePackManager {

  private final String resourcePackResourceFolderName = String.join(" ", TemplatePaperPlugin.class.getSimpleName().split("(?=[A-Z])")) + " Resource Pack";

  private final TemplatePaperPlugin plugin;

  public ResourcePackManager(TemplatePaperPlugin plugin) {
    this.plugin = plugin;

    saveResourcepackZipFile();
  }

  private void saveResourcepackZipFile() {
    plugin.getFileManager().saveResourceFileFolder(resourcePackResourceFolderName);

    try {
      FileUtil.zipFolder(Path.of(plugin.getDataPath() + File.separator + resourcePackResourceFolderName), Path.of(plugin.getDataPath() + File.separator + resourcePackResourceFolderName + ".zip"));
      File resourcePackFolder = new File(plugin.getDataPath() + File.separator + resourcePackResourceFolderName);
      FileUtils.deleteDirectory(resourcePackFolder);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}
