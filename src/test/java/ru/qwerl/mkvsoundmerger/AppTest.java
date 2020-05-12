package ru.qwerl.mkvsoundmerger;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {

  private final static App APP = new App();
  //-video "<project path>/src/test/resource/test_hierarchy/Elfen Lied [BDRemux 1080p]" -sound "Rus Sounds" "Eng Sounds" -console
  private final static String[] ARG_EXAMPLE = new String[] {
      "-video", new File("src/test/data/test_hierarchy/Elfen Lied [BDRemux 1080p]").getAbsolutePath(),
      "-sound", "Rus Sounds/first", "Eng Sounds",
      "-console",
      "-search",
      "-saveto", "/somepath"
  };

  @Test
  public void testApp() {
    APP.run(ARG_EXAMPLE);
    assertTrue(true);
  }

}
