package model;

import java.awt.Point;

import org.example.Settings;

public class Card {
    private int value;
    private String suit;
    private int x, y;
    private boolean over;

    public Card(int _value, String _suit) {
        this.value = _value;
        this.suit = _suit;
    }
    
    public int getValue() { 
        return value;
    }
    
    public String getSuit() { 
        return suit; 
    }
    
    public String getCardPath() { 
        return "/carte/" + value + "_" + suit + ".png";
    }
    
    public static Card fromString(String cardString) {
        String[] parts = cardString.split("_");
        int value = Integer.parseInt(parts[0]);
        String suit = parts[1];
        return new Card(value, suit);
    }
    
    public int getX() { 
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setX(int _newX) { 
        this.x = _newX;
    }
    
    public void setY(int _newY) { 
        this.y = _newY;
    }
    
    public boolean contains(Point point) { 
        return (point.x >= x && point.x <= x + Settings.CARD_WIDTH) && (point.y >= y && point.y <= y + Settings.CARD_HEIGHT);
    }
    
    public boolean inPlayArea() { 
        return (this.x + Settings.CARD_WIDTH/2 >= Settings.PLAYING_AREA_LEFT_BORDER && this.x + Settings.CARD_WIDTH/2 <= Settings.PLAYING_AREA_RIGHT_BORDER) &&
        (this.y + Settings.CARD_HEIGHT/2  >= Settings.PLAYING_AREA_UP_BORDER && this.y + Settings.CARD_HEIGHT/2 <= Settings.PLAYING_AREA_DOWN_BORDER);
    }
    
    public Point getPointCard() {
        return new Point(this.x, this.y);
    }
    
    public void setPoint(Point currentPoint) {
        this.x = currentPoint.x;
        this.y = currentPoint.y;
    }

    @Override
    public String toString() { 
        return value + "_" + suit; 
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public boolean isOver() {
        return over;
    }
}
