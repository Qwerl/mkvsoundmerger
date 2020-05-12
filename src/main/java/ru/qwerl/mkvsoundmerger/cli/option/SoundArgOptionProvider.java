package ru.qwerl.mkvsoundmerger.cli.option;

import org.apache.commons.cli.Option;

public class SoundArgOptionProvider implements OptionProvider {

  public static final String SOUND_ARG = "sound";

  @Override
  public Option getOption() {
    return Option.builder(SOUND_ARG)
        .required(false)
        .hasArgs()
        .desc("sound directory name")
        .build();
  }

}
