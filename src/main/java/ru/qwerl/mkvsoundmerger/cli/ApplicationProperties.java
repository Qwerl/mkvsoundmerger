package ru.qwerl.mkvsoundmerger.cli;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import ru.qwerl.mkvsoundmerger.handler.CommandHandlers;

import java.io.File;
import java.util.Set;

@Getter
@Accessors(fluent = true)
@Builder
public class ApplicationProperties {

  private File videoDirectory;
  private boolean isSearchEnabled;
  private Set<File> soundDirectories;
  private Set<File> subtitleDirectories;
  private CommandHandlers commandHandlers;

}
