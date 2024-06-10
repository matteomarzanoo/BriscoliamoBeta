package model.scores;

public abstract class Score {
    protected int wins;
    protected int lost;
    protected int ties;
    protected int totalGames;
    protected float winrate;

    public abstract int getWins();
    public abstract int getLost();
    public abstract int getTies();
    public abstract int getTotalGames();
    public abstract int getWinrate();
    public abstract void addWin();
    public abstract void addLost();
    public abstract void addTie();
    public abstract void reset();
}
