package ru.qwerl.mkvsoundmerger.cli.option;

import org.apache.commons.cli.Option;

public class VideoArgOptionProvider implements OptionProvider {

  public static final String VIDEO_ARG = "video";

  @Override
  public Option getOption() {
    return Option.builder(VIDEO_ARG)
        .hasArg(true)
        .required(true)
        .numberOfArgs(1)
        .desc("path to video directory")
        .build();
  }

}
