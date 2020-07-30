package ru.qwerl.mkvsoundmerger.crunch;

import com.google.common.collect.HashMultimap;
import ru.qwerl.mkvsoundmerger.search.VideoAttachableFileLinker;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Comparator.comparing;
import static java.util.Locale.*;
import static ru.qwerl.mkvsoundmerger.search.LinkChecker.isLinkable;

public class PlexCrunchVideoAttachableFileLinker implements VideoAttachableFileLinker<AttachableFile> {

  public Map<File, Collection<AttachableFile>> findVideoToAttachableFileLinks(Set<File> videoFiles,
                                                                              Map<File, Set<File>> attachableDirectoryToAttachableFile) {
    HashMultimap<File, AttachableFile> videoToAttachableFiles = HashMultimap.create();
    videoFiles.forEach(video -> {
          AtomicInteger directoryIndex = new AtomicInteger();
          attachableDirectoryToAttachableFile.keySet().stream()
              .sorted(comparing(File::getName))
              .forEachOrdered(directory -> {
                //directory to language map
                Locale directoryLocale = LOCALE_LIST.get(directoryIndex.getAndIncrement());
                Set<File> attachableFiles = attachableDirectoryToAttachableFile.get(directory);
                attachableFiles.stream()
                    .filter(attachableFile -> isLinkable(video, attachableFile))
                    .findAny()
                    .ifPresent(file -> videoToAttachableFiles.put(video, AttachableFile.of(directoryLocale, file)));
              });
        }
    );
    return videoToAttachableFiles.asMap();
  }

  public static final List<Locale> LOCALE_LIST = List.of(
      ENGLISH,
      FRENCH,
      GERMAN,
      ITALIAN,
      JAPANESE,
      KOREAN,
      CHINESE,
      SIMPLIFIED_CHINESE,
      TRADITIONAL_CHINESE
  );

}
