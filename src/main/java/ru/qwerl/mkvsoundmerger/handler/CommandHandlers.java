package ru.qwerl.mkvsoundmerger.handler;

import lombok.extern.slf4j.Slf4j;
import ru.qwerl.mkvsoundmerger.builder.Command;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CommandHandlers extends ArrayList<CommandHandler> {

  public void pushCommands(List<Command> commands) {
    for (int i = 0, commandsSize = commands.size(); i < commandsSize; i++) {
      Command command = commands.get(i);
      log.info("Handling command[{}/{}]", i+1, commandsSize);
      forEach(commandHandler -> commandHandler.handleCommands(command));
    }
  }
}
