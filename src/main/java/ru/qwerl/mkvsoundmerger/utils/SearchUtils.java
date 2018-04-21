package ru.qwerl.mkvsoundmerger.utils;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.qwerl.mkvsoundmerger.Format.Sound;
import ru.qwerl.mkvsoundmerger.Format.Video;

import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.joining;

public class SearchUtils {

  public static List<File> findVideoFiles(File videoDirectory) {
    List<File> foundVideoFiles = FileUtils.getAllFiles(videoDirectory, false, Video.extensionsList());
    System.out.println("FOUNDED VIDEOS: " + videoDirectory.getAbsolutePath());
    System.out.println(foundVideoFiles.stream().map(File::getName).collect(joining(lineSeparator())));
    return foundVideoFiles;
  }

  public static Map<File, Collection<File>> findSoundFiles(List<File> soundDirectories) {
    Map<File, Collection<File>> directoryFiles = new HashMap<>();
    soundDirectories.forEach(soundDirectory -> {
      List<File> foundSoundFiles = FileUtils.getAllFiles(soundDirectory, false, Sound.extensionsList());
      System.out.println("FOUNDED SOUNDS: " + soundDirectory.getAbsolutePath());
      System.out.println(foundSoundFiles.stream().map(File::getName).collect(joining(lineSeparator())));
      directoryFiles.put(soundDirectory, foundSoundFiles);
    });
    return directoryFiles;
  }

}