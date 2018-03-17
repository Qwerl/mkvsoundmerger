package ru.qwerl.mkvsoundmerger;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {

  private String[] testArgs;
  private App app;

  @Before
  public void init() {
    //-video "<project path>/src/test/resource/test_hierarchy/Elfen Lied [BDRemux 1080p]" -sound "Rus Sounds" "Eng Sounds" -console
    testArgs = new String[6];
    testArgs[0] = "-video";
    testArgs[1] = new File("src/test/data/test_hierarchy/Elfen Lied [BDRemux 1080p]").getAbsolutePath();
    testArgs[2] = "-sound";
    testArgs[3] = "Rus Sounds";
    testArgs[4] = "Eng Sounds";
    testArgs[5] = "-console";
    app = new App();
  }

  @Test
  public void testApp() {
    app.run(testArgs);
    assertTrue(true);
  }

}
