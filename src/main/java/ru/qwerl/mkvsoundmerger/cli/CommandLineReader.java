package ru.qwerl.mkvsoundmerger.cli;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import ru.qwerl.mkvsoundmerger.cli.option.OptionsConfig;
import ru.qwerl.mkvsoundmerger.handler.CommandHandler;
import ru.qwerl.mkvsoundmerger.handler.CommandHandlers;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static ru.qwerl.mkvsoundmerger.cli.option.CreateConsoleWriterOptionProvider.CONSOLE_ARG;
import static ru.qwerl.mkvsoundmerger.cli.option.CreateScriptFileOptionProvider.SAVE_FILE_ARG;
import static ru.qwerl.mkvsoundmerger.cli.option.ExecuteOptionProvider.EXECUTE_ARG;
import static ru.qwerl.mkvsoundmerger.cli.option.SavetoArgOptionProvider.SAVE_ARG;
import static ru.qwerl.mkvsoundmerger.cli.option.SearchArgOptionProvider.SEARCH_ARG;
import static ru.qwerl.mkvsoundmerger.cli.option.SoundArgOptionProvider.SOUND_ARG;
import static ru.qwerl.mkvsoundmerger.cli.option.VideoArgOptionProvider.VIDEO_ARG;
import static ru.qwerl.mkvsoundmerger.handler.CommandRunner.commandRunner;
import static ru.qwerl.mkvsoundmerger.handler.ConsoleCommandWriter.consoleCommandWriter;
import static ru.qwerl.mkvsoundmerger.handler.ScriptFileGenerator.scriptFileGenerator;

@Slf4j
public class CommandLineReader {

  private CommandLine line;

  public static ApplicationProperties readArgs(String[] args) {
    return new CommandLineReader().read(args);
  }

  @SneakyThrows
  public ApplicationProperties read(String[] args) {
    CommandLineParser parser = new DefaultParser();
    line = parser.parse(OptionsConfig.createOptions(), args);
    File mainDirectory = videoDirectory();
    return ApplicationProperties.builder()
        .isSearchEnabled(isSearchEnabled())
        .videoDirectory(mainDirectory)
        .soundDirectories(soundDirectories(mainDirectory))
        .subtitleDirectories(subtitleDirectories(mainDirectory))
        .commandHandlers(commandHandlers())
        .saveDirectory(saveDirectory())
        .build();
  }

  private File videoDirectory() {
    File videoDirectory = new File(line.getOptionValue(VIDEO_ARG));
    log.info("VIDEO DIRECTORY:");
    log.info(videoDirectory.getPath());
    return videoDirectory;
  }

  public boolean isSearchEnabled() {
    return line.hasOption(SEARCH_ARG);
  }

  private HashSet<File> soundDirectories(File mainDirectory) {
    HashSet<File> soundPaths = new HashSet<>();
    soundArgs(mainDirectory.isDirectory() ? mainDirectory : mainDirectory.getAbsoluteFile().getParentFile())
        .ifPresent(soundPaths::addAll);
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
  private Set<File> subtitleDirectories(File mainDirectory) {
    HashSet<File> subtitlePaths = new HashSet<>();
    //TODO: subtitleArgs(mainDirectory).ifPresent(subtitlePaths::addAll);
    //log.info("SUBTITLE DIRECTORIES:");
    //subtitlePaths.stream().map(File::getPath).forEach(log::info);
    return subtitlePaths;
  }

  private CommandHandlers commandHandlers() {
    CommandHandlers commandHandlers = new CommandHandlers();
    readOptionalConsoleWriter().ifPresent(commandHandlers::add);
    readOptionalSaveScript().ifPresent(commandHandlers::add);
    readOptionalExecuteCommands().ifPresent(commandHandlers::add);
    return commandHandlers;
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

  private File saveDirectory() {
    String optionalSaveDirectory = line.getOptionValue(SAVE_ARG);
    if (optionalSaveDirectory == null) return null;
    File saveDirectory = new File(optionalSaveDirectory);
    log.info("SAVE DIRECTORY:");
    log.info(saveDirectory.getPath());
    return saveDirectory;
  }

}
