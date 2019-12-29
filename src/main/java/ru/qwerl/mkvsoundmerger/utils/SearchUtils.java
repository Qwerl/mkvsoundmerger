package ru.qwerl.mkvsoundmerger.utils;

import java.io.File;
import java.util.Map;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class SearchUtils {

  public static Map<File, Set<File>> findFilesInDirectoriesByFormat(Set<File> directories, Set<String> formats) {
    return directories.stream()
        .collect(toMap(
            identity(),
            directory -> FileUtils.getAllFiles(directory, formats)
        ));
  }

}