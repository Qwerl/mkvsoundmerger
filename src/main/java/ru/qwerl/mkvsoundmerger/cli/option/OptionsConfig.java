package ru.qwerl.mkvsoundmerger.cli.option;

import org.apache.commons.cli.Options;

import java.util.List;

public class OptionsConfig {

  private final static List<OptionProvider> OPTION_PROVIDERS = List.of(
      new VideoArgOptionProvider(),
      new SavetoArgOptionProvider(),
      new SoundArgOptionProvider(),
      new SearchArgOptionProvider(),
      new ExecuteOptionProvider(),
      new CreateScriptFileOptionProvider(),
      new CreateConsoleWriterOptionProvider()
  );

  public static Options createOptions() {
    Options options = new Options();
    OPTION_PROVIDERS.forEach(optionProvider -> options.addOption(optionProvider.getOption()));
    return options;
  }

}
