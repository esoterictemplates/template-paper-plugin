package foundation.esoteric.minecraft.plugins.template.file;

import net.lingala.zip4j.ZipFile;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class FileUtil {

  private static final String FILE_EXTENSION_SEPARATOR = ".";

  private static final String FILE_MIME_TYPE_TYPE_SUBTYPE_SEPARATOR = "/";

  public static String getFileExtensionSeparator() {
    return FILE_EXTENSION_SEPARATOR;
  }

  public static String getFileMimeTypeTypeSubtypeSeparator() {
    return FILE_MIME_TYPE_TYPE_SUBTYPE_SEPARATOR;
  }

  public static void zipFolder(@NotNull File sourceFolder, File zipFile) throws IOException {
    try (ZipFile zipFileInstance = new ZipFile(zipFile)) {
      for (File file : sourceFolder.listFiles()) {
        if (file.isDirectory()) {
          zipFileInstance.addFolder(file);
        } else {
          zipFileInstance.addFile(file);
        }
      }
    }
  }
}
