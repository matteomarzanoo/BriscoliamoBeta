package model.scores;

public class OfflineScore extends Score {

    private static OfflineScore instance = null;

    private OfflineScore() {
    }

    @Override
    public int getWins() {
        return wins;
    }

    @Override
    public int getLost() {
        return lost;
    }

    @Override
    public int getTies() {
        return ties;
    }

    @Override
    public int getTotalGames() {
        return totalGames = wins + lost + ties;
    }

    @Override
    public int getWinrate() {
        if (getTotalGames() != 0) {
            winrate = ((float)getWins() / getTotalGames()) * 100;
        }
        return (int) winrate;
    }

    @Override
    public void addWin() {
        wins++;
    }

    @Override
    public void addLost() {
        lost++;
    }

    @Override
    public void addTie() {
        ties++;
    }

    @Override
    public void reset() {
        wins = 0;
        lost = 0;
        ties = 0;
        totalGames = 0;
        winrate = 0.0f;
    }

    public static OfflineScore getInstance() {
        if (instance == null) {
            instance = new OfflineScore();
        }
        return instance;
    }
}
