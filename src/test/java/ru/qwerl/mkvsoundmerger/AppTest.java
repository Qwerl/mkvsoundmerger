package ru.qwerl.mkvsoundmerger;

import java.io.File;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {

  private final static App APP = new App();

  private final static String[] VIDEO_AND_SOUND_EQUALS_NAMES = new String[] {
      "-video", new File("src/test/data/test_hierarchy/Elfen Lied [BDRemux 1080p]").getAbsolutePath(),
      "-sound", "Rus Sounds/first", "Eng Sounds",
      "-console",
      "-search",
      "-saveto", "/somepath"
  };

  @Test
  //-video "<project path>/src/test/resource/test_hierarchy/Elfen Lied [BDRemux 1080p]" -sound "Rus Sounds" "Eng Sounds" -console
  public void testApp() {
    APP.run(VIDEO_AND_SOUND_EQUALS_NAMES);
    assertTrue(true);
  }

  private final static String[] VIDEO_AND_SOUND_DIFFERENT_NAMES = new String[] {
      "-video", new File("src/test/data/test_hierarchy/Boku dake ga Inai Machi [TV][BDRemux][2016]").getAbsolutePath(),
      "-search",
      "-console"
  };

  @Test
  //-video 'Boku dake ga Inai Machi [TV][BDRemux][2016]' -search -console
  public void testApp2() {
    APP.run(VIDEO_AND_SOUND_DIFFERENT_NAMES);
    assertTrue(true);
  }

  //TODO: create CommandHandler to provide commands for tests

}