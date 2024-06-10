package model.online;

import java.util.*;
import model.*;

public class GameOnline
{
    private static GameOnline instance = null;
    private ArrayList<Card> cardsOnTheGround;
    private Player player;
    private Card briscola;
    private String scorePlayerOne;
    private String scorePlayerTwo;
    private boolean isFirstPlayer; //variabile che ci serve per ricordarci chi ha giocato la prima carta
    private boolean isGameStarted;
    private boolean myTurn;
    private final Random random = new Random();
    private int lengthHandOpponent = -1;
    private boolean gameOnlineOver = false;
    private int sizeDeck = -1;

    private GameOnline()
    {
        cardsOnTheGround = new ArrayList<>(2);
        player = new Player();
    }

    public Card playedCardPlayer(int index)
    {
        if (index >= 0 && index < player.getHandPlayer().size())
        {
            return player.getHandPlayer().remove(index);
        }
        throw new IndexOutOfBoundsException("Indice non valido per la carta giocata.");
    }

    /*Funzione singleton che servirà per creare una sola volta il game. Se richiamato, il costruttore ritornerà la stessa istanza*/
    public static GameOnline getInstance()
    {
        if (instance == null)
        {
            instance = new GameOnline();
        }
        return instance;
    }

    public GameOnline reset() {
        if (instance != null) {
            instance = null;
            instance = new GameOnline();
        }
        return instance;
    }

    // Getters Methods
    public Card getBriscola() { return briscola; }
    public ArrayList<Card> getHandPlayer(){ return this.player.getHandPlayer(); }
    public Player getPlayer() { return player; }
    public void setBriscola(Card briscola) {this.briscola = briscola;}
    public boolean isGameStarted() {return isGameStarted;}
    public void setGameStarted(boolean gameStarted) {isGameStarted = gameStarted;}
    public void setGameOnlineOver(boolean gameOnlineOver) {this.gameOnlineOver = gameOnlineOver;}
    public boolean isGameOnlineOver() {return gameOnlineOver;}
    public ArrayList<Card> getCardsOnTheGround() {return cardsOnTheGround;}
    public void clearCardsOnTheGround(){cardsOnTheGround.clear();}
    public int getLengthHandOpponent(){return this.lengthHandOpponent;}
    public void setLengthHandOpponent(int i){this.lengthHandOpponent = i;}
    public boolean isMyTurn() {return myTurn;}
    public void setMyTurn(boolean myTurn) {this.myTurn = myTurn;}
    public void setSizeDeck(int sizeDeck) {this.sizeDeck = sizeDeck;}
    public int getSizeDeck() {return sizeDeck;}

    public String getScorePlayerOne() {
        return scorePlayerOne;
    }

    public String getScorePlayerTwo() {
        return scorePlayerTwo;
    }

    public void setScorePlayerOne(String scorePlayerOne) {
        this.scorePlayerOne = scorePlayerOne;
    }

    public void setScorePlayerTwo(String scorePlayerTwo) {
        this.scorePlayerTwo = scorePlayerTwo;
    }
}
