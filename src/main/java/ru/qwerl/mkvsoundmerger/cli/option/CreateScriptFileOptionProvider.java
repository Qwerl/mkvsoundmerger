package ru.qwerl.mkvsoundmerger.cli.option;

import org.apache.commons.cli.Option;

public class CreateScriptFileOptionProvider implements OptionProvider {

  public static final String SAVE_FILE_ARG = "sf";

  @Override
  public Option getOption() {
    return Option.builder(SAVE_FILE_ARG)
        .longOpt("save_file")
        .required(false)
        .hasArg(false)
        .desc("create script file or not")
        .build();
  }

}
