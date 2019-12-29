package ru.qwerl.mkvsoundmerger.utils;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

@Slf4j
public class AttachableFileUtils {

  @SafeVarargs
  @NotNull
  public static Map<File, Set<File>> mergeAttachableFiles(Map<File, Set<File>>... attachableDirectoryToAttachableFilesForMergeMaps) {
    Map<File, Set<File>> attachableDirectoryToAttachableFiles = new HashMap<>();
    Arrays.stream(attachableDirectoryToAttachableFilesForMergeMaps)
        .forEach(attachableDirectoryToAttachableFilesForMerge -> attachableDirectoryToAttachableFilesForMerge
            .forEach((file, files) -> attachableDirectoryToAttachableFiles.merge(file, files, combineSets()))
        );
    return attachableDirectoryToAttachableFiles;
  }

  @NotNull
  private static BiFunction<Set<File>, Set<File>, Set<File>> combineSets() {
    return (set1, set2) -> {
      set1.addAll(set2);
      return set1;
    };
  }

}
