import org.junit.Test;

import static org.junit.Assert.*;

public class BowlingGameTest {

  @Test
  public void testAllStrike() throws Exception {
    String bowlingCode = "X|X|X|X|X|X|X|X|X|X||XX";
    assertEquals(new BowlingGame().getBowlingScore(bowlingCode), 300);
  }

  @Test
  public void testSecondSpare() throws Exception {
    String bowlingCode = "5/|5/|5/|5/|5/|5/|5/|5/|5/|5/||5";
    assertEquals(new BowlingGame().getBowlingScore(bowlingCode), 150);
  }

  @Test
  public void testSecondMiss() throws Exception {
    String bowlingCode = "9-|9-|9-|9-|9-|9-|9-|9-|9-|9-||";
    assertEquals(new BowlingGame().getBowlingScore(bowlingCode), 90);
  }

  @Test
  public void testOtherSituation() throws Exception {
    String bowlingCode = "X|7/|9-|X|-8|8/|-6|X|X|X||81";
    assertEquals(new BowlingGame().getBowlingScore(bowlingCode), 167);
  }

  @Test
  public void testOtherSituationK() throws Exception {
    for (int i = 0; i < 1000; i += 1) {
      testOtherSituation();
    }
  }

}

