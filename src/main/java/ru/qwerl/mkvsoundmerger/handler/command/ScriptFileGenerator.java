package ru.qwerl.mkvsoundmerger.handler.command;

import java.util.List;

public class ScriptFileGenerator implements CommandHandler {

  public static ScriptFileGenerator scriptFileGenerator() {
    return new ScriptFileGenerator();
  }

  public void handleCommands(List<String> commands) {
    //TODO: create file and write commands there
  }

}