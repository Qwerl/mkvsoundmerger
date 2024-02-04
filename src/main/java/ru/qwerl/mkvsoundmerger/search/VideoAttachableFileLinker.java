package ru.qwerl.mkvsoundmerger.search;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static ru.qwerl.mkvsoundmerger.search.LinkChecker.isLinkable;

public class VideoAttachableFileLinker {

  public static Map<File, Collection<File>> findVideoToAttachableFileLinks(Set<File> videoFiles,
                                                                           Map<File, Set<File>> attachableDirectoryToAttachableFile) {
    Multimap<File, File> videoToAttachableFiles = HashMultimap.create();
    videoFiles.forEach((video) ->
        attachableDirectoryToAttachableFile.values().forEach((attachableFiles) ->
            attachableFiles.stream()
                .filter(attachableFile -> isLinkable(video, attachableFile))
                .findAny()
                .ifPresent(file -> videoToAttachableFiles.put(video, file))
        )
    );
    return videoToAttachableFiles.asMap();
  }

}
