package ru.qwerl.mkvsoundmerger.handler.command;

import java.util.List;

public interface CommandHandler {

  void handleCommands(List<String> commands);

}