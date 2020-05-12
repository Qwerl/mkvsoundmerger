package ru.qwerl.mkvsoundmerger.cli.option;

import org.apache.commons.cli.Option;

public class ExecuteOptionProvider implements OptionProvider {

  public static final String EXECUTE_ARG = "exec";

  @Override
  public Option getOption() {
    return Option.builder(EXECUTE_ARG)
        .longOpt("execute_commands")
        .required(false)
        .hasArg(false)
        .desc("execute commands or not")
        .build();
  }

}
