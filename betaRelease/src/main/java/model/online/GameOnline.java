package model.online;

import java.util.*;
import model.*;

public class GameOnline
{
    private static GameOnline instance = null;
    private ArrayList<Card> cardsOnTheGround;
    private Player player;
    private Card briscola;
    private boolean myTurn;
    private int lengthHandOpponent = -1;
    private boolean gameOnlineOver = false;
    private int sizeDeck = -1;
    private List<String> nicknamesPlayer;
    private List<String> scoresPlayer;

    private GameOnline()
    {
        cardsOnTheGround = new ArrayList<>(2);
        player = new Player();
        nicknamesPlayer = new ArrayList<>(2);
        scoresPlayer = new ArrayList<>(2) ;
    }

    public Card playedCardPlayer(int index)
    {
        if (index >= 0 && index < player.getHandPlayer().size())
        {
            return player.getHandPlayer().remove(index);
        }
        throw new IndexOutOfBoundsException("Indice non valido per la carta giocata.");
    }

    public static GameOnline getInstance()
    {
        if (instance == null)
        {
            instance = new GameOnline();
        }
        return instance;
    }

    public void reset() {
        if (instance != null) {
            instance = null;
            instance = new GameOnline();
        }
    }

    public Card getBriscola() { return briscola; }
    public ArrayList<Card> getHandPlayer(){ return this.player.getHandPlayer(); }
    public Player getPlayer() { return player; }
    public void setBriscola(Card briscola) {this.briscola = briscola;}
    public void setGameOnlineOver(boolean gameOnlineOver) {this.gameOnlineOver = gameOnlineOver;}
    public boolean isGameOnlineOver() {return gameOnlineOver;}
    public ArrayList<Card> getCardsOnTheGround() {return cardsOnTheGround;}
    public int getLengthHandOpponent(){return this.lengthHandOpponent;}
    public void setLengthHandOpponent(int i){this.lengthHandOpponent = i;}
    public boolean isMyTurn() {return myTurn;}
    public void setMyTurn(boolean myTurn) {this.myTurn = myTurn;}
    public void setSizeDeck(int sizeDeck) {this.sizeDeck = sizeDeck;}
    public int getSizeDeck() {return sizeDeck;}
    public List<String> getNicknamesPlayer() {return nicknamesPlayer;}

    public List<String> getScoresPlayer() {
        return scoresPlayer;
    }

}
