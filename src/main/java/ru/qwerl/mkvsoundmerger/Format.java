package ru.qwerl.mkvsoundmerger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class Format {

  public enum Video {
    MKV(".mkv"),
    AVI(".avi");
    private String extension;

    Video(String extension) {
      this.extension = extension;
    }

    public String extension() {
      return extension;
    }

    public static Set<String> extensionsList() {
      return stream(values())
          .map(Video::extension)
          .collect(Collectors.toSet());
    }

  }

  public enum Sound {
    MKA(".mka"),
    AC3(".ac3");
    private String extension;

    Sound(String extension) {
      this.extension = extension;
    }

    public String extension() {
      return extension;
    }

    public static Set<String> extensionsList() {
      return stream(values())
          .map(Sound::extension)
          .collect(Collectors.toSet());
    }

  }

  public enum Subtitle {
    ASS(".ass");
    private String extension;

    Subtitle(String extension) {
      this.extension = extension;
    }

    public String extension() {
      return extension;
    }

    public static Set<String> extensionsList() {
      return stream(values())
          .map(Subtitle::extension)
          .collect(Collectors.toSet());
    }
  }

  public static Set<String> attachableFilesExtensions() {
    Set<String> extensionsList = new HashSet<>();
    extensionsList.addAll(Sound.extensionsList());
    extensionsList.addAll(Subtitle.extensionsList());
    return extensionsList;
  }

}