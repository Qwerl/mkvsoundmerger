package ru.qwerl.mkvsoundmerger.utils;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class FileUtils {

  public static Set<File> getAllFiles(File curDir, Set<String> formats) {
    return getAllFiles(curDir, false, formats);
  }

  public static Set<File> getAllFiles(File curDir, boolean depthSearchApplied, Set<String> formats) {
    Set<File> foundFiles = new HashSet<>();
    File[] filesList = curDir.listFiles();
    for (File f : filesList != null ? filesList : new File[0]) {
      if (f.isDirectory() && depthSearchApplied) {
        foundFiles.addAll(getAllFiles(f, true, formats));
      }
      if (f.isFile() && formats.stream().anyMatch(format -> f.getName().toLowerCase().endsWith(format))) {
        foundFiles.add(f);
      }
    }
    return foundFiles;
  }

}
