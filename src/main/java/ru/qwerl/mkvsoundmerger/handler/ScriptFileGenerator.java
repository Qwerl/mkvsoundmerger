package ru.qwerl.mkvsoundmerger.handler;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.APPEND;

@Slf4j
public class ScriptFileGenerator implements CommandHandler {

  private static final String ARGS_SEPARATOR = " ";
  public static final String WRAPPER = "\"";
  private final File scriptDirectory;

  public static ScriptFileGenerator scriptFileGenerator(File mainDirectory) {
    return new ScriptFileGenerator(mainDirectory);
  }

  private ScriptFileGenerator(File mainDirectory) {
    scriptDirectory = new File(mainDirectory, mainDirectory.getName() + ".sh");
    try {
      scriptDirectory.createNewFile();
    } catch (IOException e) {
      log.error("Error while creating file", e);
    }
  }

  @SneakyThrows
  public void handleCommands(List<String> commands) {
    String command = commands.stream()
            .map(this::wrapArg)
            .collect(Collectors.joining(ARGS_SEPARATOR));
    Files.write(scriptDirectory.toPath(), (command + "\n").getBytes(), APPEND);
  }

  private String wrapArg(String arg) {
    return String.format("%s%s%s", WRAPPER, arg, WRAPPER);
  }

}