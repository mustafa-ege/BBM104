public class Score {
    int score;

    public Score() {
        score = 0;
    }

    public void addRedBallPoints() {
        score += 10;
    }

    public void addYellowBallPoints() {
        score += 5;
    }

    public void subtractBlackBallPoints() {
        score -= 5;
    }

    public int getScore() {
        return score;
    }
}
