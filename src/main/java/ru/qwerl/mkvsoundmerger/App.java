package ru.qwerl.mkvsoundmerger;

import ru.qwerl.mkvsoundmerger.builder.Command;
import ru.qwerl.mkvsoundmerger.cli.ApplicationProperties;
import ru.qwerl.mkvsoundmerger.cli.CommandLineReader;
import ru.qwerl.mkvsoundmerger.search.Format.Video;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ru.qwerl.mkvsoundmerger.builder.CommandBuilder.buildCommands;
import static ru.qwerl.mkvsoundmerger.search.AttachableFileFinder.searchByPaths;
import static ru.qwerl.mkvsoundmerger.search.AttachableFileFinder.searchInMainDirectory;
import static ru.qwerl.mkvsoundmerger.search.VideoAttachableFileLinker.findVideoToAttachableFileLinks;
import static ru.qwerl.mkvsoundmerger.utils.FileUtils.getAllFiles;

public class App {

  public static void main(String[] args) {
    new App().run(args);
  }

  void run(String[] args) {
    ApplicationProperties properties = CommandLineReader.readArgs(args);
    Set<File> videoFiles = getAllFiles(properties.videoDirectory(), Video.extensions());
    Map<File, Set<File>> attachableDirectoryToAttachableFiles = properties.isSearchEnabled()
        ? searchInMainDirectory(properties.videoDirectory())
        : searchByPaths(properties.soundDirectories(), properties.subtitleDirectories());
    Map<File, Collection<File>> videoToAttachableFiles = findVideoToAttachableFileLinks(videoFiles, attachableDirectoryToAttachableFiles);
    List<Command> commands = buildCommands(videoToAttachableFiles, properties.saveDirectory());
    properties.commandHandlers().pushCommands(commands);
  }

}