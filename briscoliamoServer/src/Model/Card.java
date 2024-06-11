package Model;

import java.util.Objects;

public class Card {
    private int value;
    private String suit;

    public Card(int _value, String _suit)
    {
        this.value = _value;
        this.suit = _suit;
    }
    public int getValue() { return value;}
    public String getSuit() { return suit; }
    @Override
    public String toString() { return value + "_" + suit; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return value == card.value && Objects.equals(suit, card.suit);
    }

    public static Card fromString(String cardString)
    {
        String[] parts = cardString.split("_");
        int value = Integer.parseInt(parts[0]);
        String suit = parts[1];
        return new Card(value, suit);
    }
}
