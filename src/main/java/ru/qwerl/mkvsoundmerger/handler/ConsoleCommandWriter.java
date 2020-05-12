package ru.qwerl.mkvsoundmerger.handler;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ConsoleCommandWriter implements CommandHandler {

  private static final String ARGS_SEPARATOR = " ";
  public static final String WRAPPER = "\"";

  public static ConsoleCommandWriter consoleCommandWriter() {
    return new ConsoleCommandWriter();
  }

  public void handleCommands(List<String> commands) {
    String command = commands.stream()
        .map(this::wrapArg)
        .collect(Collectors.joining(ARGS_SEPARATOR));
    log.info("COMMAND:");
    log.info(command);
  }

  private String wrapArg(String arg) {
    return String.format("%s%s%s", WRAPPER, arg, WRAPPER);
  }

}
