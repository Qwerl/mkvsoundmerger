package ru.qwerl.mkvsoundmerger.crunch;

import lombok.AllArgsConstructor;

import java.io.File;
import java.util.Locale;

@AllArgsConstructor(staticName = "of")
public class AttachableFile {
  Locale localeCrunch;
  File attachableFile;
}
