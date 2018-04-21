package ru.qwerl.mkvsoundmerger;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.cli.*;
import ru.qwerl.mkvsoundmerger.handler.command.CommandHandler;
import ru.qwerl.mkvsoundmerger.handler.command.ConsoleCommandWriter;

import static java.lang.System.lineSeparator;
import static java.util.Optional.empty;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static ru.qwerl.mkvsoundmerger.handler.command.CommandRunner.commandRunner;
import static ru.qwerl.mkvsoundmerger.handler.command.ConsoleCommandWriter.consoleCommandWriter;
import static ru.qwerl.mkvsoundmerger.handler.command.ScriptFileGenerator.scriptFileGenerator;

public class CommandLineReader {

  private static final String SOUND_ARG = "sound";
  private static final String VIDEO_ARG = "video";
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

  public List<File> soundDirectories(File videoDirectory) {
    String[] parsedSoundDirectories = line.getOptionValues(SOUND_ARG);
    List<File> soundDirectories = Arrays.stream(parsedSoundDirectories)
        .map(directory -> new File(videoDirectory.getPath() + "/" + directory))
        .collect(toList());
    System.out.println("SOUND DIRECTORIES:");
    System.out.println(soundDirectories.stream().map(File::getPath).collect(joining(lineSeparator())));
    return soundDirectories;
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
        .addOption(execute())
        .addOption(createScriptFile())
        .addOption(createConsoleWriter());
  }

  private Option soundArg() {
    return Option.builder(SOUND_ARG)
        .required(true)
        .hasArgs()
        .desc("Sound directory name")
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
        .desc("Create script file or not")
        .build();
  }

  private Option execute() {
    return Option.builder(EXECUTE_ARG)
        .longOpt("execute_commands")
        .required(false)
        .hasArg(false)
        .desc("Execute commands or not")
        .build();
  }

  private Option createConsoleWriter() {
    return Option.builder(CONSOLE_ARG)
        .required(false)
        .hasArg(false)
        .desc("Write commands to console")
        .build();
  }

}
