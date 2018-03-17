package ru.qwerl.mkvsoundmerger.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

  public static List<File> getAllFiles(File curDir, boolean depthSearchApplied, String format) {
    List<File> foundFiles = new ArrayList<>();
    File[] filesList = curDir.listFiles();
    for (File f : filesList != null ? filesList : new File[0]) {
      if (f.isDirectory() && depthSearchApplied) {
        foundFiles.addAll(getAllFiles(f, true, format));
      }
      if (f.isFile() && f.getName().toLowerCase().endsWith(format)) {
        foundFiles.add(f);
      }
    }
    return foundFiles;
  }

}
