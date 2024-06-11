package model;

import java.util.ArrayList;

public class Player {
    
    private static String nickname;
    private ArrayList<Card> playerHand;

    public Player() {
        nickname = "defaultPlayer";
        playerHand = new ArrayList<>(3);
    }

    public ArrayList<Card> getHandPlayer() {
        return playerHand;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String name) {
        nickname = name;
    }
}
