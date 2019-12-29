package ru.qwerl.mkvsoundmerger.handler;

import java.util.List;

public interface CommandHandler {

  void handleCommands(List<String> commands);

}