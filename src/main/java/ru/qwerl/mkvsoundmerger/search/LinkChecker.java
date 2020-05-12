package ru.qwerl.mkvsoundmerger.search;

import java.io.File;

import static org.apache.commons.io.FilenameUtils.removeExtension;

public class LinkChecker {

  public static boolean isLinkable(File video, File attachableFile) {
    //TODO: add more ways to find links
    return fileNamesEquals(video, attachableFile)
        || soundFileContainsVideoFileName(video, attachableFile)
        || videoFileContainsSoundFileName(video, attachableFile);
  }

  private static boolean fileNamesEquals(File video, File sound) {
    return removeExtension(sound.getName()).equals(removeExtension(video.getName()));
  }

  private static boolean soundFileContainsVideoFileName(File video, File sound) {
    return removeExtension(sound.getName()).contains(removeExtension(video.getName()));
  }

  private static boolean videoFileContainsSoundFileName(File video, File sound) {
    return removeExtension(video.getName()).contains(removeExtension(sound.getName()));
  }

}
