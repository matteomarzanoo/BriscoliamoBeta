package model;

import java.util.ArrayList;

public class Player {
    
    private static String playerName;
    private ArrayList<Card> playerHand;

    public Player() { 
        playerName = "defaultPlayer";
        playerHand = new ArrayList<>(3);
    }
    
    public String getName() { 
        return playerName; 
    }
    
    public ArrayList<Card> getHandPlayer() { 
        return playerHand; 
    }

    public void addCardToHand(Card card) { 
        playerHand.add(card); 
    }

    public void setName(String name) {
        playerName = name; 
    }

}
