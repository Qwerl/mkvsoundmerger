package ru.qwerl.mkvsoundmerger.search;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Map;
import java.util.Set;

import static ru.qwerl.mkvsoundmerger.search.Format.attachableFilesExtensions;
import static ru.qwerl.mkvsoundmerger.search.AttachableFileDirectoryFinder.findAttachableFileDirectories;
import static ru.qwerl.mkvsoundmerger.utils.AttachableFileUtils.mergeAttachableFiles;
import static ru.qwerl.mkvsoundmerger.utils.SearchUtils.findFilesInDirectoriesByFormat;

public class AttachableFileFinder {

  @NotNull
  public static Map<File, Set<File>> searchInDirectory(File mainDirectory) {
    Set<File> attachableFileDirectories = findAttachableFileDirectories(mainDirectory);
    return findFilesInDirectoriesByFormat(attachableFileDirectories, attachableFilesExtensions());
  }

  @NotNull
  public static Map<File, Set<File>> searchByConcretePaths(Set<File> soundDirectories, Set<File> subtitleDirectories) {
    Map<File, Set<File>> soundDirectoryToSoundFiles = findFilesInDirectoriesByFormat(soundDirectories, Format.Sound.extensionsList());
    Map<File, Set<File>> subtitleDirectoryToSubtitleFiles = findFilesInDirectoriesByFormat(subtitleDirectories, Format.Subtitle.extensionsList());
    return mergeAttachableFiles(soundDirectoryToSoundFiles, subtitleDirectoryToSubtitleFiles);
  }

}
