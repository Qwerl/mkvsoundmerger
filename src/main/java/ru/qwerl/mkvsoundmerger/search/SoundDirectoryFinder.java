package ru.qwerl.mkvsoundmerger.search;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import ru.qwerl.mkvsoundmerger.Format;
import ru.qwerl.mkvsoundmerger.utils.FileUtils;

/**
 * @author Askar
 */
public class SoundDirectoryFinder {

  @NotNull
  public List<File> searchIn(File directory) {
    return getAllDirectoriesThatContainsSoundFiles(directory);
  }

  @NotNull
  private List<File> getAllDirectoriesThatContainsSoundFiles(File directory) {
    List<File> foundDirectoriesWithSoundFiles = new ArrayList<>();
    File[] directoriesList = directory.listFiles(File::isDirectory);
    for (File dir : directoriesList != null ? directoriesList : new File[0]) {
      foundDirectoriesWithSoundFiles.addAll(getAllDirectoriesThatContainsSoundFiles(dir));
      if (isContainsAnySound(dir)) {
        foundDirectoriesWithSoundFiles.add(dir);
      }
    }
    return foundDirectoriesWithSoundFiles;
  }

  boolean isContainsAnySound(File directory) {
    List<File> allFiles = FileUtils.getAllFiles(directory, false, Format.Sound.extensionsList());
    return !allFiles.isEmpty();
  }

}
