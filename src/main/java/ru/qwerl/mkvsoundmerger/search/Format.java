package ru.qwerl.mkvsoundmerger.search;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class Format {

  public enum Video {
    MKV(".mkv"),
    AVI(".avi");
    private final String extension;

    Video(String extension) {
      this.extension = extension;
    }

    public String extension() {
      return extension;
    }

    public static Set<String> extensions() {
      return stream(values())
          .map(Video::extension)
          .collect(Collectors.toSet());
    }

  }

  public enum Sound {
    MKA(".mka"),
    AAC(".aac"),
    AC3(".ac3");
    private final String extension;

    Sound(String extension) {
      this.extension = extension;
    }

    public String extension() {
      return extension;
    }

    public static Set<String> extensions() {
      return stream(values())
          .map(Sound::extension)
          .collect(Collectors.toSet());
    }

  }

  public enum Subtitle {
    ASS(".ass");
    private final String extension;

    Subtitle(String extension) {
      this.extension = extension;
    }

    public String extension() {
      return extension;
    }

    public static Set<String> extensions() {
      return stream(values())
          .map(Subtitle::extension)
          .collect(Collectors.toSet());
    }
  }

  public static Set<String> attachableFilesExtensions() {
    Set<String> extensionsList = new HashSet<>();
    extensionsList.addAll(Sound.extensions());
    extensionsList.addAll(Subtitle.extensions());
    return extensionsList;
  }

}