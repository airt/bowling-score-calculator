package impl;

import java.util.Arrays;

public class JBowlingScoreCalculator {

  public static int calculate(String bowlingCode) {
    int totalScore = 0;
    int prevBallScore = 0;
    int[] rewardBallsXs = {};
    boolean isPrevCharSep = false;
    boolean isExtraChance = false;

    for (char c : bowlingCode.toCharArray()) {
      if (isExtraChance) {
        final int currBallScore = selectScore(c, prevBallScore);
        final int rewardScore = calcRewardScore(currBallScore, rewardBallsXs);

        totalScore += rewardScore;
        rewardBallsXs = nextRewardBallsXs(rewardBallsXs, 0);
        isPrevCharSep = false;
        isExtraChance = true;

      } else if (c == '|') {
        if (isPrevCharSep) {
          isExtraChance = true;
        } else {
          isPrevCharSep = true;
        }

      } else {
        final int currBallScore = selectScore(c, prevBallScore);
        final int rewardBalls = selectRewardBalls(c);
        final int rewardScore = calcRewardScore(currBallScore, rewardBallsXs);

        totalScore += currBallScore + rewardScore;
        rewardBallsXs = nextRewardBallsXs(rewardBallsXs, rewardBalls);
        prevBallScore = currBallScore;
        isPrevCharSep = false;
      }
    }

    return totalScore;
  }

  private static int selectScore(char c, int prevBallScore) {
    switch (c) {
      case 'X':
        return 10;
      case '/':
        return 10 - prevBallScore;
      case '-':
        return 0;
      default:
        return c - '0';
    }
  }

  private static int selectRewardBalls(char c) {
    switch (c) {
      case 'X':
        return 2;
      case '/':
        return 1;
      default:
        return 0;
    }
  }

  private static int calcRewardScore(
    int currBallScore,
    int[] rewardBallsXs
  ) {
    return currBallScore * rewardBallsXs.length;
  }

  private static int[] nextRewardBallsXs(
    int[] rewardBallsXs,
    int rewardBalls
  ) {
    // ((rewardBallsXs map decrease) + rewardBalls) filter positive

    int[] newRewardBallsXs = new int[rewardBallsXs.length + 1];
    int indexOfNewRewardBallsXs = 0;

    for (final int cRewardBalls : rewardBallsXs) {
      if (cRewardBalls > 1) {
        newRewardBallsXs[indexOfNewRewardBallsXs] = cRewardBalls - 1;
        indexOfNewRewardBallsXs += 1;
      }
    }

    if (rewardBalls > 0) {
      newRewardBallsXs[indexOfNewRewardBallsXs] = rewardBalls;
      indexOfNewRewardBallsXs += 1;
    }

    return Arrays.copyOf(newRewardBallsXs, indexOfNewRewardBallsXs);
  }

}
