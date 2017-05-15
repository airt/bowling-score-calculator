package impl

object BowlingScoreCalculator {

  def calculate(bowlingCode: String): Int =
    calcNext(bowlingCode.toList)

  @annotation.tailrec
  private def calcNext(
    chars: List[Char],
    totalScore: Int = 0,
    rewardBallsXs: Set[Int] = Set.empty,
    prevBallScore: Int = 0,
    isPrevCharSep: Boolean = false,
    isExtraChance: Boolean = false
  ): Int = chars match {
    case Nil => totalScore

    case c :: cs if isExtraChance =>
      val (currBallScore, _) = selectScoreAndRewardBalls(c, prevBallScore)
      val rewardScore = calcRewardScore(currBallScore, rewardBallsXs)
      calcNext(
        cs,
        totalScore = totalScore + rewardScore,
        rewardBallsXs = nextRewardBallsXs(rewardBallsXs, 0),
        isExtraChance = true
      )

    case '|' :: cs if isPrevCharSep =>
      calcNext(
        cs, totalScore, rewardBallsXs,
        isExtraChance = true
      )

    case '|' :: cs =>
      calcNext(
        cs, totalScore, rewardBallsXs, prevBallScore,
        isPrevCharSep = true
      )

    case c :: cs =>
      val (currBallScore, rewardBalls) = selectScoreAndRewardBalls(c, prevBallScore)
      val rewardScore = calcRewardScore(currBallScore, rewardBallsXs)
      calcNext(
        cs,
        totalScore = totalScore + currBallScore + rewardScore,
        rewardBallsXs = nextRewardBallsXs(rewardBallsXs, rewardBalls),
        prevBallScore = currBallScore
      )
  }

  private def selectScoreAndRewardBalls(
    char: Char,
    prevBallScore: Int
  ) = (char: @annotation.switch) match {
    case 'X' => (10, 2)
    case '/' => (10 - prevBallScore, 1)
    case '-' => (0, 0)
    case _ => (char.asDigit, 0)
  }

  private def calcRewardScore(
    currBallScore: Int,
    rewardBallsXs: Set[Int]
  ) = currBallScore * rewardBallsXs.size

  private def nextRewardBallsXs(
    rewardBallsXs: Set[Int],
    rewardBalls: Int
  ) = ((rewardBallsXs map decrease) + rewardBalls) filter positive

  private def decrease(x: Int) = x - 1

  private def positive(x: Int) = x > 0

}
