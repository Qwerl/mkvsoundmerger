package ru.qwerl.mkvsoundmerger.cli.option;

import org.apache.commons.cli.Option;

public class SearchArgOptionProvider implements OptionProvider {

  public static final String SEARCH_ARG = "search";

  @Override
  public Option getOption() {
    return Option.builder(SEARCH_ARG)
        .required(false)
        .hasArg(false)
        .desc("enabling directory search")
        .build();
  }

}
