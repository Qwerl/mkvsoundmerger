package ru.qwerl.mkvsoundmerger.handler.command;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ConsoleCommandWriter implements CommandHandler {
  private static final String ARGS_SEPARATOR = " ";

  public static ConsoleCommandWriter consoleCommandWriter() {
    return new ConsoleCommandWriter();
  }

  public void handleCommands(List<String> commands) {
    String command = String.join(ARGS_SEPARATOR, commands);
    log.info("COMMAND:");
    log.info(command);
  }

}
