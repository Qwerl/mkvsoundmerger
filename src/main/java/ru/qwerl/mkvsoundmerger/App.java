package ru.qwerl.mkvsoundmerger;

import java.io.File;
import java.util.*;

import com.google.common.collect.HashMultimap;
import org.jetbrains.annotations.NotNull;
import ru.qwerl.mkvsoundmerger.Format.Sound;
import ru.qwerl.mkvsoundmerger.Format.Subtitle;
import ru.qwerl.mkvsoundmerger.Format.Video;
import ru.qwerl.mkvsoundmerger.handler.command.CommandHandler;
import ru.qwerl.mkvsoundmerger.utils.FileUtils;

import static java.nio.file.FileSystems.getDefault;
import static org.apache.commons.io.FilenameUtils.removeExtension;
import static ru.qwerl.mkvsoundmerger.CommandLineReader.readArgs;
import static ru.qwerl.mkvsoundmerger.Format.attachableFilesExtensions;
import static ru.qwerl.mkvsoundmerger.utils.SearchUtils.findFilesInDirectoriesByFormat;

public class App {

  private static final String PATH_SEPARATOR = getDefault().getSeparator();

  public static void main(String[] args) {
    new App().run(args);
  }

  void run(String[] args) {
    CommandLineReader reader = readArgs(args);

    File videoDirectory = reader.videoDirectory();
    Set<File> videoFiles = FileUtils.getAllFiles(videoDirectory, Video.extensionsList());

    Map<File, Set<File>> attachableDirectoryToAttachableFiles;
    if (reader.isSearchEnabled()) {
      Set<File> attachableFilesDirectories = reader.attachableFilesDirectories(videoDirectory);
      attachableDirectoryToAttachableFiles = findFilesInDirectoriesByFormat(attachableFilesDirectories, attachableFilesExtensions());
    }
    else {
      Set<File> soundDirectories = reader.soundDirectories(videoDirectory);
      Set<File> subtitleDirectories = reader.subtitleDirectories(videoDirectory);
      Map<File, Set<File>> soundDirectoryToSoundFiles = findFilesInDirectoriesByFormat(soundDirectories, Sound.extensionsList());
      Map<File, Set<File>> subtitleDirectoryToSubtitleFiles = findFilesInDirectoriesByFormat(subtitleDirectories, Subtitle.extensionsList());
      attachableDirectoryToAttachableFiles = mergeAttachableFiles(soundDirectoryToSoundFiles, subtitleDirectoryToSubtitleFiles);
    }
    Map<File, Collection<File>> videoToAttachableFiles = createLinkVideoToAttachableFile(videoFiles, attachableDirectoryToAttachableFiles);
    List<List<String>> commandsToAttachableFileDirectory = buildCommands(videoToAttachableFiles);
    sendCommandsToHandlers(commandsToAttachableFileDirectory, reader.commandHandlers());
  }

  @SafeVarargs
  @NotNull
  private final Map<File, Set<File>> mergeAttachableFiles(Map<File, Set<File>>... attachableDirectoryToAttachableFilesForMergeMaps) {
    Map<File, Set<File>> attachableDirectoryToAttachableFiles = new HashMap<>();
    Arrays.stream(attachableDirectoryToAttachableFilesForMergeMaps)
        .forEach(attachableDirectoryToAttachableFilesForMerge ->
            attachableDirectoryToAttachableFilesForMerge
                .forEach((file, files) -> attachableDirectoryToAttachableFiles.merge(file, files, (attachableFiles, mergeFiles) -> {
                  attachableFiles.addAll(mergeFiles);
                  return attachableFiles;
                }))
        );
    return attachableDirectoryToAttachableFiles;
  }

  private void sendCommandsToHandlers(List<List<String>> commandsToSoundDirectory, List<CommandHandler> commandHandlers) {
    commandsToSoundDirectory.forEach(commands ->
        commandHandlers.forEach(commandHandler ->
            commandHandler.handleCommands(commands)
        )
    );
  }

  private List<List<String>> buildCommands(Map<File, Collection<File>> combine) {
    List<List<String>> commands = new ArrayList<>();
    combine.forEach((video, attachableFile) -> commands.add(buildCommand(video, attachableFile)));
    return commands;
  }

  private List<String> buildCommand(File video, Collection<File> attachableFiles) {
    List<String> commands = new ArrayList<>();
    commands.add("mkvmerge");
    commands.add("-o");
    commands.add(getOutputVideoAbsolutePath(video));
    commands.add(video.getPath());
    attachableFiles.forEach(attachableFile -> commands.add(attachableFile.getPath()));
    return commands;
  }

  private String getOutputVideoAbsolutePath(File video) {
    return video.getParentFile().getAbsolutePath() + PATH_SEPARATOR + "combined" + PATH_SEPARATOR + video.getName();
  }

  private Map<File, Collection<File>> createLinkVideoToAttachableFile(Set<File> videoFiles, Map<File, Set<File>> attachableDirectoryToAttachableFile) {
    HashMultimap<File, File> videoToAttachableFiles = HashMultimap.create();
    videoFiles.forEach((video) ->
        attachableDirectoryToAttachableFile.forEach((directory, files) -> files
            .stream()
            .filter(file -> isAttachable(video, file))
            .findAny()
            .ifPresent(file -> videoToAttachableFiles.put(video, file))
        )
    );
    return videoToAttachableFiles.asMap();
  }

  private boolean isAttachable(File video, File attachableFile) {
    //TODO: add more ways to find link
    return fileNamesEquals(video, attachableFile) ||
        soundFileContainsVideoFileName(video, attachableFile);
  }

  private boolean fileNamesEquals(File video, File sound) {
    return removeExtension(sound.getName()).equals(removeExtension(video.getName()));
  }

  private boolean soundFileContainsVideoFileName(File video, File sound) {
    return removeExtension(sound.getName()).contains(removeExtension(video.getName()));
  }

  private CommandLineReader readArgs(String[] args) {
    return new CommandLineReader()
        .read(args);
  }

}