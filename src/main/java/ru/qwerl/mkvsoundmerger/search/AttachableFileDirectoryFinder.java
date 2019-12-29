package ru.qwerl.mkvsoundmerger.search;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Set;

@Slf4j
@AllArgsConstructor
public class AttachableFileDirectoryFinder {

  public static Set<File> findAttachableFileDirectories(File directory) {
    Set<File> attachableFileDirectories = FileDirectoryFinder.searchIn(directory, Format.attachableFilesExtensions());
    log.info("ATTACHABLE FILE DIRECTORIES:");
    attachableFileDirectories.stream().map(File::getPath).forEach(log::info);
    return attachableFileDirectories;
  }

}
