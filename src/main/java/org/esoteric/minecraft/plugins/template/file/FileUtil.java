package org.esoteric.minecraft.plugins.template.file;

import net.lingala.zip4j.ZipFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

  public static @Nullable String getSha1HexString(File file) {
    String algorithm = "SHA-1";

    MessageDigest digest;
    try (InputStream fileInputStream = new FileInputStream(file)) {
      digest = MessageDigest.getInstance(algorithm);

      int n = 0;
      byte[] buffer = new byte[8192];

      while (n != -1) {
        n = fileInputStream.read(buffer);
        if (n > 0) {
          digest.update(buffer, 0, n);
        }
      }
    } catch (IOException | NoSuchAlgorithmException exception) {
      exception.printStackTrace();
      return null;
    }

    byte[] hashBytes = digest.digest();
    StringBuilder hexString = new StringBuilder(2 * hashBytes.length);
    for (byte hashByte : hashBytes) {
      String hex = Integer.toHexString(0xff & hashByte);

      if (hex.length() == 1) {
        hexString.append('0');
      }

      hexString.append(hex);
    }

    return hexString.toString();
  }
}
