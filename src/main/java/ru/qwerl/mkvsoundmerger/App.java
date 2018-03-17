package ru.qwerl.mkvsoundmerger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.HashMultimap;
import ru.qwerl.mkvsoundmerger.handler.command.CommandHandler;

import static java.nio.file.FileSystems.getDefault;
import static org.apache.commons.io.FilenameUtils.removeExtension;
import static ru.qwerl.mkvsoundmerger.utils.SearchUtils.findSoundFiles;
import static ru.qwerl.mkvsoundmerger.utils.SearchUtils.findVideoFiles;

public class App {

  private static final String PATH_SEPARATOR = getDefault().getSeparator();

  public static void main(String[] args) {
    new App().run(args);
  }

  public void run(String[] args) {
    CommandLineReader reader = readArgs(args);
    File videoDirectory = reader.videoDirectory();
    List<File> soundDirectories = reader.soundDirectories(videoDirectory);
    List<CommandHandler> commandHandlers = reader.commandHandlers();

    List<File> videoFiles = findVideoFiles(videoDirectory);
    Map<File, Collection<File>> soundFiles = findSoundFiles(soundDirectories);

    Map<File, Collection<File>> soundsToVideo = createLinkSoundsToVideo(videoFiles, soundFiles);

    List<List<String>> commandsToSoundDirectory = prepareCommands(soundsToVideo);
    sendCommandsToHandlers(commandsToSoundDirectory, commandHandlers);
  }

  private void sendCommandsToHandlers(List<List<String>> commandsToSoundDirectory, List<CommandHandler> commandHandlers) {
    commandsToSoundDirectory.forEach(commands ->
        commandHandlers.forEach(commandHandler ->
            commandHandler.handleCommands(commands)
        )
    );
  }

  private List<List<String>> prepareCommands(Map<File, Collection<File>> combine) {
    List<List<String>> commands = new ArrayList<>();
    combine.forEach((video, sounds) -> commands.add(prepareCommandsForCurrentVideo(video, sounds)));
    return commands;
  }

  private List<String> prepareCommandsForCurrentVideo(File video, Collection<File> sounds) {
    List<String> commands = new ArrayList<>();
    commands.add("mkvmerge");
    commands.add("-o");
    commands.add(getOutputVideoAbsolutePath(video));
    commands.add(video.getPath());
    sounds.forEach(sound -> commands.add(sound.getPath()));
    return commands;
  }

  private String getOutputVideoAbsolutePath(File video) {
    return video.getParentFile().getAbsolutePath() + PATH_SEPARATOR + "combined" + PATH_SEPARATOR + video.getName();
  }

  private Map<File, Collection<File>> createLinkSoundsToVideo(List<File> videoFiles, Map<File, Collection<File>> soundDirectories) {
    HashMultimap<File, File> videoToSounds = HashMultimap.create();
    videoFiles.forEach((video) ->
        soundDirectories.forEach((soundDirectory, soundFiles) -> soundFiles
            .stream()
            .filter(sound -> isAttachable(video, sound))
            .findAny()
            .ifPresent(sound -> videoToSounds.put(video, sound))
        )
    );
    return videoToSounds.asMap();
  }

  private boolean isAttachable(File video, File sound) {
    //TODO: add more ways to find link
    return removeExtension(sound.getName()).equals(removeExtension(video.getName()));
  }

  private CommandLineReader readArgs(String[] args) {
    return new CommandLineReader()
        .read(args);
  }

}