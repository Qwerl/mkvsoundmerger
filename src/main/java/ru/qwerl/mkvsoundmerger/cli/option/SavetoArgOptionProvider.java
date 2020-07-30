package ru.qwerl.mkvsoundmerger.cli.option;

import org.apache.commons.cli.Option;

public class SavetoArgOptionProvider implements OptionProvider {

  public static final String SAVE_ARG = "saveto";

  @Override
  public Option getOption() {
    return Option.builder(SAVE_ARG)
        .hasArg(true)
        .required(false)
        .numberOfArgs(1)
        .desc("directory to save combined files")
        .build();
  }

}
