package net.slqmy.template_paper_plugin.file;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;
import java.io.IOException;
import java.net.URL;

public class FileUtil {
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
          .map(name -> resourceFileFolderPath + name.substring(resourceFileFolderPath.length())).toList();

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
}
