package ru.qwerl.mkvsoundmerger.handler;

import lombok.SneakyThrows;

import java.util.List;

public class CommandRunner implements CommandHandler {

  public static CommandRunner commandRunner() {
    return new CommandRunner();
  }

    @SneakyThrows
    public void handleCommands(List<String> commands) {
        Process process = new ProcessBuilder(commands)
            .inheritIO()
            .start();
        process.waitFor();
    }

}