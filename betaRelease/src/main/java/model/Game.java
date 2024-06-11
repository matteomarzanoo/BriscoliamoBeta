package model;

import java.util.*;

public abstract class Game {
    protected ArrayList<Card> cardsOnTheGround;
    protected Player player;
    protected Card briscola;
    protected boolean isFirstPlayer;
    protected boolean myTurn;
    protected final Random random = new Random();

    public Game() {
        cardsOnTheGround = new ArrayList<>(2);
        player = new Player();
    }

    public abstract void start();
    public abstract void distributeHand();
    public abstract void playedCardPlayer(int index);
    public abstract boolean isGameOver();
    public abstract void determineWinner();

    public Card getBriscola() { return briscola; }
    public ArrayList<Card> getHandPlayer() { return this.player.getHandPlayer(); }
    public Player getPlayer() { return player; }
    public ArrayList<Card> getCardsOnTheGround() { return cardsOnTheGround; }
    public void clearCardsOnTheGround() { cardsOnTheGround.clear(); }
    public boolean isMyTurn() { return myTurn; }
    public void setMyTurn(boolean myTurn) { this.myTurn = myTurn; }
    public boolean isFirstPlayer() { return isFirstPlayer; }
    public void setFirstPlayer(boolean isFirstPlayer) { this.isFirstPlayer = isFirstPlayer; }
}
