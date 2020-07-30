package ru.qwerl.mkvsoundmerger;

import ru.qwerl.mkvsoundmerger.builder.Command;
import ru.qwerl.mkvsoundmerger.cli.ApplicationProperties;
import ru.qwerl.mkvsoundmerger.cli.CommandLineReader;
import ru.qwerl.mkvsoundmerger.crunch.AttachableFile;
import ru.qwerl.mkvsoundmerger.crunch.PlexCrunchCommandBuilder;
import ru.qwerl.mkvsoundmerger.crunch.PlexCrunchVideoAttachableFileLinker;
import ru.qwerl.mkvsoundmerger.search.Format;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ru.qwerl.mkvsoundmerger.search.AttachableFileFinder.searchByConcretePaths;
import static ru.qwerl.mkvsoundmerger.search.AttachableFileFinder.searchInDirectory;
import static ru.qwerl.mkvsoundmerger.utils.FileUtils.getAllFiles;

public class AppWithPlexWorkaround {

  public static void main(String[] args) {
    new AppWithPlexWorkaround().run(args);
  }

  //TODO: extract runners
  void run(String[] args) {
    ApplicationProperties properties = CommandLineReader.readArgs(args);
    Set<File> videoFiles = getAllFiles(properties.videoDirectory(), Format.Video.extensionsList());
    Map<File, Set<File>> attachableDirectoryToAttachableFiles = properties.isSearchEnabled()
        ? searchInDirectory(properties.videoDirectory())
        : searchByConcretePaths(properties.soundDirectories(), properties.subtitleDirectories());
    Map<File, Collection<AttachableFile>> videoToAttachableFiles = new PlexCrunchVideoAttachableFileLinker()
        .findVideoToAttachableFileLinks(videoFiles, attachableDirectoryToAttachableFiles);
    List<Command> commands = PlexCrunchCommandBuilder.buildCommands(videoToAttachableFiles, properties.saveDirectory());
    properties.commandHandlers().pushCommands(commands);
  }

}
