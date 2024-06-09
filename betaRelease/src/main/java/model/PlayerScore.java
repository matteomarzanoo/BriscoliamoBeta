package model;

public class PlayerScore {
    private int wins;
    private int lost;
    private int ties;
    private int totalGames;
    private float winrate;
    private static PlayerScore instance = null;

    private PlayerScore(){
    }

    public int getWins(){
        return wins;
    }

    public int getLost(){
        return lost;
    }
    
    public int getTies(){
        return ties;
    }

    public int getTotalGames(){
        return totalGames;
    }

    public float getWinrate(){
        if(totalGames!=0){
        return (wins/totalGames)*100;}
        else {return 0;}
    }

    public void addWin(){
        wins++;
    }

    public void addLost(){
        lost++;
    }

    public void addTie(){
        ties++;
    }

    public void setTotalGames(){
        totalGames = wins + lost + ties;
    }

    public void reset(){
        wins = 0;
        lost = 0;
        ties = 0;
        totalGames = 0;
    }

    public static PlayerScore getInstance(){
        if (instance == null) {
            instance = new PlayerScore();            
        }
        return instance;
    }
}
