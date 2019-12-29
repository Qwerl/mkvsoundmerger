package ru.qwerl.mkvsoundmerger.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.nio.file.FileSystems.getDefault;

public class CommandBuilder {

  private static final String PATH_SEPARATOR = getDefault().getSeparator();

  public static List<Command> buildCommands(Map<File, Collection<File>> videoToAttachableFiles) {
    List<Command> commands = new ArrayList<>();
    videoToAttachableFiles.forEach((video, attachableFile) -> commands.add(buildCommand(video, attachableFile)));
    return commands;
  }

  public static Command buildCommand(File video, Collection<File> attachableFiles) {
    Command commands = new Command();
    commands.add("mkvmerge");
    commands.add("-o");
    commands.add(getOutputVideoAbsolutePath(video));
    commands.add(video.getPath());
    attachableFiles.forEach(attachableFile -> commands.add(attachableFile.getPath()));
    return commands;
  }

  private static String getOutputVideoAbsolutePath(File video) {
    return video.getParentFile().getAbsolutePath() + PATH_SEPARATOR + "combined" + PATH_SEPARATOR + video.getName();
  }

}
