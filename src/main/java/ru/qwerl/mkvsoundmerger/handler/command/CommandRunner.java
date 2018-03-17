package ru.qwerl.mkvsoundmerger.handler.command;

import java.io.IOException;
import java.util.List;

public class CommandRunner implements CommandHandler {

  public static CommandRunner commandRunner() {
    return new CommandRunner();
  }

  public void handleCommands(List<String> commands) {
    try {
      Process process = new ProcessBuilder(commands)
          .inheritIO()
          .start();
      process.waitFor();
    }
    catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}