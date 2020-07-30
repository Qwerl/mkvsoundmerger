package ru.qwerl.mkvsoundmerger.search;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface VideoAttachableFileLinker<T> {

  Map<File, Collection<T>> findVideoToAttachableFileLinks(Set<File> videoFiles, Map<File, Set<File>> attachableDirectoryToAttachableFile);

}
