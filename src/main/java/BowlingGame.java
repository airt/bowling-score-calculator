public class BowlingGame {

  @SuppressWarnings("WeakerAccess")
  public int getBowlingScore(String bowlingCode) {
    return impl.JBowlingScoreCalculator.calculate(bowlingCode);
  }

}
