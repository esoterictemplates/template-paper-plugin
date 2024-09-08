package net.slqmy.template_paper_plugin.resource_pack;

import java.io.File;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;

import net.slqmy.template_paper_plugin.TemplatePaperPlugin;
import net.slqmy.template_paper_plugin.file.FileUtil;
import net.slqmy.template_paper_plugin.resource_pack.event.listeners.PlayerJoinListener;

public class ResourcePackManager {

  private final TemplatePaperPlugin plugin;

  private final String resourcePackResourceFolderName = String.join(" ", TemplatePaperPlugin.class.getSimpleName().split("(?=[A-Z])")) + " Resource Pack";

  private File resourcePackZipFile;

  public File getResourcePackZipFile() {
    return resourcePackZipFile;
  }

  public ResourcePackManager(TemplatePaperPlugin plugin) {
    this.plugin = plugin;

    saveResourcepackZipFile();

    Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(plugin, this), plugin);
  }

  private void saveResourcepackZipFile() {
    plugin.getFileManager().saveResourceFileFolder(resourcePackResourceFolderName);

    try {
      FileUtil.zipFolder(Path.of(plugin.getDataPath() + File.separator + resourcePackResourceFolderName), Path.of(plugin.getDataPath() + File.separator + resourcePackResourceFolderName + ".zip"));
      resourcePackZipFile = new File(plugin.getDataPath() + File.separator + resourcePackResourceFolderName + ".zip");

      File resourcePackFolder = new File(plugin.getDataPath() + File.separator + resourcePackResourceFolderName);
      FileUtils.deleteDirectory(resourcePackFolder);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}
