package net.slqmy.template_paper_plugin.file;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.jar.JarEntry;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileUtil {

  private static final String FILE_EXTENSION_SEPARATOR = ".";

  public static String getFileExtensionSeparator() {
    return FILE_EXTENSION_SEPARATOR;
  }

  public static List<String> getResourceFileFolderResourceFilePaths(String resourceFileFolderPath) throws IOException {
    ClassLoader classLoader = FileUtil.class.getClassLoader();

    URL jarURL = classLoader.getResource(resourceFileFolderPath);
    if (jarURL == null) {
      return Collections.emptyList();
    }

    String jarPath = jarURL.getPath();
    int exclamationMarkIndex = jarPath.indexOf("!");

    String jarPathPrefix = "file:";
    String jarFilePath = jarPath.substring(jarPathPrefix.length(), exclamationMarkIndex);

    try (JarFile jarFile = new JarFile(jarFilePath)) {
      List<String> paths = jarFile.stream().map(JarEntry::getName).filter(name -> name.startsWith(resourceFileFolderPath) && !name.equals(resourceFileFolderPath))
          .map(name -> name.substring(resourceFileFolderPath.length())).filter(name -> !"/".equals(name)).map(name -> resourceFileFolderPath + name).toList();

      return paths;
    }
  }

  public static List<String> getResourceFileFolderResourceFilePathsRecursively(String resourceFileFolderPath) throws IOException {
    List<String> paths = new ArrayList<>();

    for (String resourceFilePath : getResourceFileFolderResourceFilePaths(resourceFileFolderPath)) {
      List<String> subFiles = getResourceFileFolderResourceFilePathsRecursively(resourceFilePath);
      if (subFiles.isEmpty()) {
        paths.add(resourceFilePath);
      } else {
        paths.addAll(subFiles);
      }
    }

    return paths;
  }

  public static void zipFolder(Path sourceFolderPath, Path zipPath) throws Exception {
    ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipPath.toFile()));
    Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<Path>() {
      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
        zipOutputStream.putNextEntry(new ZipEntry(sourceFolderPath.relativize(file).toString()));
        Files.copy(file, zipOutputStream);
        zipOutputStream.closeEntry();
        return FileVisitResult.CONTINUE;
      }
    });
    zipOutputStream.close();
  }

  public static String getSha1HexString(File file) {
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
