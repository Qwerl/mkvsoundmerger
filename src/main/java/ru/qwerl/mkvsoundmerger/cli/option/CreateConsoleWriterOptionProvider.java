package ru.qwerl.mkvsoundmerger.cli.option;

import org.apache.commons.cli.Option;

public class CreateConsoleWriterOptionProvider implements OptionProvider {

  public static final String CONSOLE_ARG = "console";

  @Override
  public Option getOption() {
    return Option.builder(CONSOLE_ARG)
        .required(false)
        .hasArg(false)
        .desc("write commands to console")
        .build();
  }

}
