package ru.qwerl.mkvsoundmerger.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.nio.file.FileSystems.getDefault;

public class CommandBuilder {

  private static final String PATH_SEPARATOR = getDefault().getSeparator();

  public static List<Command> buildCommands(Map<File, Collection<File>> videoToAttachableFiles, File saveDirectory) {
    List<Command> commands = new ArrayList<>();
    videoToAttachableFiles.forEach((video, attachableFile) -> commands.add(buildCommand(video, attachableFile, saveDirectory)));
    return commands;
  }

  private static Command buildCommand(File video, Collection<File> attachableFiles, File saveDirectory) {
    Command commands = new Command();
    commands.add("mkvmerge");
    commands.add("-o");
    commands.add(getOutputVideoAbsolutePath(video, saveDirectory));
    commands.add(video.getPath());
    attachableFiles.forEach(attachableFile -> {
      commands.add("--track-name");
      commands.add("0:" + attachableFile.getName());
      commands.add(attachableFile.getPath());
    });
    return commands;
  }

  private static String getOutputVideoAbsolutePath(File video, File saveDirectory) {
    String saveTo = saveDirectory != null
        ? saveDirectory.getAbsolutePath()
        : video.getAbsoluteFile().getParentFile().getAbsolutePath() + PATH_SEPARATOR + "combined";
    return saveTo + PATH_SEPARATOR + video.getName();
  }

}
