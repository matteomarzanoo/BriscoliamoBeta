package model;

import java.util.ArrayList;
import java.util.Random;

public class Bot {
    private String botName;
    private ArrayList<Card> botHand;
    private final Random random = new Random();

    public Bot(){
        botName = generateName();
        botHand = new ArrayList<>(3);
    }

    public String getBotName() { return botName; }
    public ArrayList<Card> getHandBot() { return botHand; }
    private String generateName(){
        final String[] animals = {"Cat", "Dog","Sheep", "Cow", "Horse", "Chicken", "Pig", "Bee","Rabbit", "Duck"};
        final String[] adjectives = {"Good", "Hot", "Cold", "Small", "Free", "Hungry", "Happy", "Old", "Cute","Young"};
        int i = random.nextInt(adjectives.length);
        int j = random.nextInt(animals.length);
        return adjectives[i] + animals[j];
    }

}
