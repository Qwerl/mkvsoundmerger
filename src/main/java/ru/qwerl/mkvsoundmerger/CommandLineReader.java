package ru.qwerl.mkvsoundmerger;

import java.io.File;
import java.util.*;

import org.apache.commons.cli.*;
import ru.qwerl.mkvsoundmerger.handler.command.CommandHandler;
import ru.qwerl.mkvsoundmerger.search.FileDirectoryFinder;

import java.io.File;
import java.util.*;

import static java.util.Arrays.stream;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static ru.qwerl.mkvsoundmerger.handler.command.CommandRunner.commandRunner;
import static ru.qwerl.mkvsoundmerger.handler.command.ConsoleCommandWriter.consoleCommandWriter;
import static ru.qwerl.mkvsoundmerger.handler.command.ScriptFileGenerator.scriptFileGenerator;

public class CommandLineReader {

  private static final String VIDEO_ARG = "video";
  private static final String SOUND_ARG = "sound";
  private static final String SEARCH_ARG = "search";
  private static final String EXECUTE_ARG = "exec";
  private static final String SAVE_FILE_ARG = "sf";
  private static final String CONSOLE_ARG = "console";

  private CommandLine line;

  public CommandLineReader read(String[] args) {
    try {
      CommandLineParser parser = new DefaultParser();
      line = parser.parse(createOptions(), args);
      return this;
    }
    catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  private Optional<CommandHandler> readOptionalSaveScript() {
    return line.hasOption(SAVE_FILE_ARG)
        ? Optional.of(scriptFileGenerator())
        : empty();
  }

  private Optional<CommandHandler> readOptionalExecuteCommands() {
    return line.hasOption(EXECUTE_ARG)
        ? Optional.of(commandRunner())
        : empty();
  }

  private Optional<CommandHandler> readOptionalConsoleWriter() {
    return line.hasOption(CONSOLE_ARG)
        ? Optional.of(consoleCommandWriter())
        : empty();
  }

  public File videoDirectory() {
    File videoDirectory = new File(line.getOptionValue(VIDEO_ARG));
    System.out.println("VIDEO DIRECTORY:");
    System.out.println(videoDirectory.getPath());
    return videoDirectory;
  }

  public HashSet<File> soundDirectories(File videoDirectory) {
    HashSet<File> soundPaths = new HashSet<>();
    soundArgs(videoDirectory).ifPresent(soundPaths::addAll);
    soundSearchEnabled(videoDirectory).ifPresent(soundPaths::addAll);
    System.out.println("SOUND DIRECTORIES:");
    System.out.println(soundPaths.stream().map(File::getPath).collect(joining(lineSeparator())));
    return soundPaths;
  }

  //NOT implemented eet
  public Set<File> subtitleDirectories(File videoDirectory) {
    HashSet<File> subtitlePaths = new HashSet<>();
    //TODO: subtitleArgs(videoDirectory).ifPresent(subtitlePaths::addAll);
    log.info("SUBTITLE DIRECTORIES:");
    subtitlePaths.stream().map(File::getPath).forEach(log::info);
    return subtitlePaths;
  }

  public Set<File> attachableFilesDirectories(File directory) {
    Set<File> attachableFileDirectories = FileDirectoryFinder.searchIn(directory, Format.attachableFilesExtensions());
    log.info("ATTACHABLE FILE DIRECTORIES:");
    attachableFileDirectories.stream().map(File::getPath).forEach(log::info);
    return attachableFileDirectories;
  }

  private Optional<List<File>> soundArgs(File videoDirectory) {
    return ofNullable(line.getOptionValues(SOUND_ARG))
        .map(soundDirectoryArgs ->
            stream(soundDirectoryArgs)
                .map(directory -> new File(videoDirectory.getPath() + "/" + directory))
                .collect(toList())
        );
  }

  public boolean isSearchEnabled() {
    return line.hasOption(SEARCH_ARG);
  }

  public List<CommandHandler> commandHandlers() {
    List<CommandHandler> commandHandlers = new ArrayList<>();
    readOptionalExecuteCommands().ifPresent(commandHandlers::add);
    readOptionalSaveScript().ifPresent(commandHandlers::add);
    readOptionalConsoleWriter().ifPresent(commandHandlers::add);
    return commandHandlers;
  }

  private Options createOptions() {
    return new Options()
        .addOption(videoArg())
        .addOption(soundArg())
        .addOption(searchArg())
        .addOption(execute())
        .addOption(createScriptFile())
        .addOption(createConsoleWriter());
  }

  private Option soundArg() {
    return Option.builder(SOUND_ARG)
        .required(false)
        .hasArgs()
        .desc("sound directory name")
        .build();
  }

  private Option searchArg() {
    return Option.builder(SEARCH_ARG)
        .required(false)
        .hasArg(false)
        .desc("enabling directory search")
        .build();
  }

  private Option videoArg() {
    return Option.builder(VIDEO_ARG)
        .hasArg(true)
        .required(true)
        .numberOfArgs(1)
        .desc("path to video directory")
        .build();
  }

  private Option createScriptFile() {
    return Option.builder(SAVE_FILE_ARG)
        .longOpt("save_file")
        .required(false)
        .hasArg(false)
        .desc("create script file or not")
        .build();
  }

  private Option execute() {
    return Option.builder(EXECUTE_ARG)
        .longOpt("execute_commands")
        .required(false)
        .hasArg(false)
        .desc("execute commands or not")
        .build();
  }

  private Option createConsoleWriter() {
    return Option.builder(CONSOLE_ARG)
        .required(false)
        .hasArg(false)
        .desc("write commands to console")
        .build();
  }

}
