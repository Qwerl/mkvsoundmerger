package ru.qwerl.mkvsoundmerger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    testArgs = new String[7];
    testArgs[0] = "-video";
    testArgs[1] = new File("src/test/data/test_hierarchy/Elfen Lied [BDRemux 1080p]").getAbsolutePath();
    testArgs[2] = "-sound";
    testArgs[3] = "Rus Sounds/first";
    testArgs[4] = "Eng Sounds";
    testArgs[5] = "-console";
    testArgs[6] = "-search";
    app = new App();
  }

  @Test
  public void testApp() {
    app.run(testArgs);
    assertTrue(true);
  }

}
