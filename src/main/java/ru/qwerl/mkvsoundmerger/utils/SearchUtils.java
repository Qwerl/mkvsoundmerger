package ru.qwerl.mkvsoundmerger.utils;

import java.io.File;
import java.util.*;

public class SearchUtils {

  public static Map<File, Set<File>> findFilesInDirectoriesByFormat(Set<File> directories, Set<String> formats) {
    Map<File, Set<File>> directoryFiles = new HashMap<>();
    directories.forEach(directory -> {
      Set<File> foundSoundFiles = FileUtils.getAllFiles(directory, formats); //TODO: depthSearchApplied maybe?
      directoryFiles.put(directory, foundSoundFiles);
    });
    return directoryFiles;
  }

}