package ru.qwerl.mkvsoundmerger.handler;

import ru.qwerl.mkvsoundmerger.builder.Command;

import java.util.ArrayList;
import java.util.List;

public class CommandHandlers extends ArrayList<CommandHandler> {

  public void pushCommands(List<Command> commands) {
    commands.forEach(command ->
        this.forEach(commandHandler -> commandHandler
            .handleCommands(command)));
  }

}
