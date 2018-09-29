package ru.qwerl.mkvsoundmerger.search;

import org.jetbrains.annotations.NotNull;
import ru.qwerl.mkvsoundmerger.utils.FileUtils;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Askar
 */
public class FileDirectoryFinder {

  @NotNull
  public static Set<File> searchIn(File directory, Set<String> formats) {
    return getAllDirectoriesThatContainsFilesWithFormat(directory, formats);
  }

  @NotNull
  private static Set<File> getAllDirectoriesThatContainsFilesWithFormat(File directory, Set<String> formats) {
    Set<File> foundDirectoriesWithSoundFiles = new HashSet<>();
    File[] directoriesList = directory.listFiles(File::isDirectory);
    for (File dir : directoriesList != null ? directoriesList : new File[0]) {
      foundDirectoriesWithSoundFiles.addAll(getAllDirectoriesThatContainsFilesWithFormat(dir, formats));
      if (isContainsAnyFileWithFormat(dir, formats)) {
        foundDirectoriesWithSoundFiles.add(dir);
      }
    }
    return foundDirectoriesWithSoundFiles;
  }

  private static boolean isContainsAnyFileWithFormat(File directory, Set<String> formats) {
    Set<File> allFiles = FileUtils.getAllFiles(directory, formats);
    return !allFiles.isEmpty();
  }

}
