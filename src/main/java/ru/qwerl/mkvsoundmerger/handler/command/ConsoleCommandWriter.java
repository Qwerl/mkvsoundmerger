package ru.qwerl.mkvsoundmerger.handler.command;

import java.util.List;

public class ConsoleCommandWriter implements CommandHandler {

  private static final String ARGS_SEPARATOR = " ";

  public static ConsoleCommandWriter consoleCommandWriter() {
    return new ConsoleCommandWriter();
  }

  public void handleCommands(List<String> commands) {
    String command = String.join(ARGS_SEPARATOR, commands);
    System.out.println(command);
  }

}
