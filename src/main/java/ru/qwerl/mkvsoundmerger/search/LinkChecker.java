package ru.qwerl.mkvsoundmerger.search;

import java.io.File;
import java.util.function.Supplier;

import static org.apache.commons.io.FilenameUtils.removeExtension;

public class LinkChecker {

  public static boolean isLinkable(File video, File attachableFile) {
    //TODO: add more ways to find links
    return fileNamesEquals(video, attachableFile)
        || soundFileContainsVideoFileName(video, attachableFile)
        || videoFileContainsSoundFileName(video, attachableFile)
        || containsSameNumbersInFileName(video, attachableFile);
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

  private static boolean containsSameNumbersInFileName(File video, File sound) {
    String videoNumbers = removePopularNumbers(extractNumbers(video.getName()));
    String soundNumbers = removePopularNumbers(extractNumbers(sound.getName()));
    boolean containsAnyNumbers = !videoNumbers.isEmpty() && !soundNumbers.isEmpty();
    Supplier<Boolean> haveCorrelation = () -> videoNumbers.contains(soundNumbers) || soundNumbers.contains(videoNumbers);
    return containsAnyNumbers && haveCorrelation.get();
  }

  private static String extractNumbers(String name) {
    return name.chars()
        .filter(Character::isDigit)
        .collect(
            StringBuilder::new,
            StringBuilder::appendCodePoint,
            StringBuilder::append
        )
        .toString();
  }

  private static String removePopularNumbers(String forChange) {
    return forChange
        .replace("1920", "").replace("1080", "")
        .replace("1280", "").replace("720", "")
        .replace("264", "").replace("265", "");
  }

}
