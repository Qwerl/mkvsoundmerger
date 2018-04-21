package ru.qwerl.mkvsoundmerger;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class Format {

  public enum Video {
    MKV(".mkv");

    private String extension;

    Video(String extension) {
      this.extension = extension;
    }

    public String extension() {
      return extension;
    }

    public static List<String> extensionsList() {
      return stream(values())
          .map(Video::extension)
          .collect(Collectors.toList());
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

    public static List<String> extensionsList() {
      return stream(values())
          .map(Sound::extension)
          .collect(Collectors.toList());
    }

  }

}