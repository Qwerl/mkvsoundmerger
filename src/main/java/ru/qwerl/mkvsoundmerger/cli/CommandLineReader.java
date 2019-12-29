package ru.qwerl.mkvsoundmerger.cli;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;
import ru.qwerl.mkvsoundmerger.handler.CommandHandler;
import ru.qwerl.mkvsoundmerger.handler.CommandHandlers;

import java.io.File;
import java.util.*;

import static java.util.Arrays.stream;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static ru.qwerl.mkvsoundmerger.handler.CommandRunner.commandRunner;
import static ru.qwerl.mkvsoundmerger.handler.ConsoleCommandWriter.consoleCommandWriter;
import static ru.qwerl.mkvsoundmerger.handler.ScriptFileGenerator.scriptFileGenerator;

@Slf4j
public class CommandLineReader {

  private static final String VIDEO_ARG = "video";
  private static final String SOUND_ARG = "sound";
  private static final String SEARCH_ARG = "search";
  private static final String EXECUTE_ARG = "exec";
  private static final String SAVE_FILE_ARG = "sf";
  private static final String CONSOLE_ARG = "console";

  private CommandLine line;

  public static ApplicationProperties readArgs(String[] args) {
    return new CommandLineReader().read(args);
  }

  @SneakyThrows
  public ApplicationProperties read(String[] args) {
    CommandLineParser parser = new DefaultParser();
    line = parser.parse(createOptions(), args);
    File mainDirectory = videoDirectory();
    return ApplicationProperties.builder()
        .isSearchEnabled(isSearchEnabled())
        .videoDirectory(mainDirectory)
        .soundDirectories(soundDirectories(mainDirectory))
        .subtitleDirectories(subtitleDirectories(mainDirectory))
        .commandHandlers(commandHandlers())
        .build();
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
    log.info("VIDEO DIRECTORY:");
    log.info(videoDirectory.getPath());
    return videoDirectory;
  }

  public HashSet<File> soundDirectories(File mainDirectory) {
    HashSet<File> soundPaths = new HashSet<>();
    soundArgs(mainDirectory).ifPresent(soundPaths::addAll);
    log.info("SOUND DIRECTORIES:");
    soundPaths.stream().map(File::getPath).forEach(log::info);
    return soundPaths;
  }

  private Optional<List<File>> soundArgs(File mainDirectory) {
    return ofNullable(line.getOptionValues(SOUND_ARG))
        .map(soundDirectoryArgs ->
            stream(soundDirectoryArgs)
                .map(directory -> new File(mainDirectory.getPath() + "/" + directory))
                .collect(toList())
        );
  }

  //NOT implemented yet
  public Set<File> subtitleDirectories(File mainDirectory) {
    HashSet<File> subtitlePaths = new HashSet<>();
    //TODO: subtitleArgs(mainDirectory).ifPresent(subtitlePaths::addAll);
    log.info("SUBTITLE DIRECTORIES:");
    subtitlePaths.stream().map(File::getPath).forEach(log::info);
    return subtitlePaths;
  }

  public boolean isSearchEnabled() {
    return line.hasOption(SEARCH_ARG);
  }

  public CommandHandlers commandHandlers() {
    CommandHandlers commandHandlers = new CommandHandlers();
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
