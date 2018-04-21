package ru.qwerl.mkvsoundmerger.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

  public static List<File> getAllFiles(File curDir, boolean depthSearchApplied, List<String> formats) {
    List<File> foundFiles = new ArrayList<>();
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
