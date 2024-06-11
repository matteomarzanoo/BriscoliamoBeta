package model;

import model.scores.OfflineScore;

import java.util.*;

public class GameOffline extends Game {
    private static GameOffline instance = null;
    private ArrayList<Card> deck;
    private Bot bot;
    private int scorePlayer;
    private int scoreBot;
    private boolean isDealerPlayer;
    private Card tempAddedCard;
    private String suitPlayer;
    private String suitBot;
    private Map<Integer, Integer> valuesMap;
    private boolean imWinner;
    private int handWinner;
    private boolean isOver = false;
    private OfflineScore offlineScore;
    private int currentScorePlayer = -1;
    private boolean paused = false;

    private GameOffline() {
        deck = new ArrayList<>();
        player = new Player();
        bot = new Bot();
        offlineScore = OfflineScore.getInstance();
        scoreBot = 0;
        scorePlayer = 0;

        isDealerPlayer = chooseDealer();
        isFirstPlayer = !isDealerPlayer;
        myTurn = !isDealerPlayer;

        if(myTurn)
        {
            handWinner = 1;
        }

        valuesMap = initValuesMap();
        start();
    }

    public static GameOffline getInstance() {
        if (instance == null) {
            instance = new GameOffline();
        }
        return instance;
    }


    public void start() {
        initializeDeck();
        distributeHand();
        briscola = chooseBriscola();

        if(!myTurn)
        {
            playedCardBot();
        }
    }

    private void initializeDeck() {
        String[] suits = {"B", "C", "O", "S"};
        for (int i = 1; i <= 10; i++)
        {
            for (String suit : suits)
            {
                deck.add(new Card(i, suit));
            }
        }
        Collections.shuffle(deck);
    }

    private Map<Integer, Integer> initValuesMap() {
        Map<Integer, Integer> cardValue = new HashMap<>();
        cardValue.put(1, 11);
        cardValue.put(2, 0);
        cardValue.put(3, 10);
        cardValue.put(4, 0);
        cardValue.put(5, 0);
        cardValue.put(6, 0);
        cardValue.put(7, 0);
        cardValue.put(8, 2);
        cardValue.put(9, 3);
        cardValue.put(10, 4);
        return cardValue;
    }

    public Card chooseBriscola() {
        return briscola = deck.get(0);
    }

    public boolean chooseDealer() {
        return random.nextBoolean();
    }

    public void distributeHand() {
        for (int i = 0; i < 3; i++) {
            player.getHandPlayer().add(deck.remove(deck.size() - 1));
            bot.getHandBot().add(deck.remove(deck.size() - 1));
        }
    }



    public void addCard(ArrayList<Card> hand) {
        if (hand.size() < 3) {
            if (!deck.isEmpty()) {
                tempAddedCard = deck.removeLast();
                hand.add(tempAddedCard);
            }
        }
    }

    public void playedCardPlayer(int indexCurrentCardPlayer) {
        if(myTurn){
            if(cardsOnTheGround.size() != 2)
                cardsOnTheGround.add(player.getHandPlayer().get(indexCurrentCardPlayer));
            changeTurn();
            player.getHandPlayer().remove(indexCurrentCardPlayer);
        }
    }

    public Card playedCardBot() {
        if(!myTurn && !bot.getHandBot().isEmpty()){
            int index = random.nextInt(bot.getHandBot().size());
            Card currentCard = bot.getHandBot().remove(index);
            cardsOnTheGround.add(currentCard);
            changeTurn();
            return currentCard;
        } else {
            return null;
        }
    }

    public void attributePoints() {
        if (cardsOnTheGround.size() == 2) {
            Card playedCardPlayer;
            Card playedCardBot;
            if (isFirstPlayer) {
                playedCardPlayer = cardsOnTheGround.get(0);
                playedCardBot = cardsOnTheGround.get(1);
            } else {
                playedCardPlayer = cardsOnTheGround.get(1);
                playedCardBot = cardsOnTheGround.get(0);
            }
            calculateScore(playedCardPlayer, playedCardBot);
        }
    }

    public void calculateScore(Card playedCardPlayer, Card playedCardBot) {
        suitPlayer = playedCardPlayer.getSuit();
        suitBot = playedCardBot.getSuit();

        if (suitPlayer.equals(suitBot)) {
            if (valuesMap.get(playedCardPlayer.getValue()) > valuesMap.get(playedCardBot.getValue())) {
                currentScorePlayer = valuesMap.get(playedCardPlayer.getValue()) + valuesMap.get(playedCardBot.getValue());
                scorePlayer += currentScorePlayer;
                isFirstPlayer = true;
                handWinner = 1;
            } else {
                scoreBot += valuesMap.get(playedCardBot.getValue()) + valuesMap.get(playedCardPlayer.getValue());
                currentScorePlayer = -1;
                isFirstPlayer = false;
                handWinner = 0;
            }
        } else if (suitPlayer.equals(briscola.getSuit())) {
            currentScorePlayer = valuesMap.get(playedCardPlayer.getValue()) + valuesMap.get(playedCardBot.getValue());
            scorePlayer += currentScorePlayer;
            isFirstPlayer = true;
            handWinner = 1;
        } else if (suitBot.equals(briscola.getSuit())) {
            scoreBot += valuesMap.get(playedCardBot.getValue()) + valuesMap.get(playedCardPlayer.getValue());
            currentScorePlayer = -1;
            isFirstPlayer = false;
            handWinner = 0;
        } else {
            if (isFirstPlayer) {
                currentScorePlayer = valuesMap.get(playedCardPlayer.getValue()) + valuesMap.get(playedCardBot.getValue());
                scorePlayer += currentScorePlayer;
                isFirstPlayer = true;
                handWinner = 1;
            } else {
                scoreBot += valuesMap.get(playedCardBot.getValue()) + valuesMap.get(playedCardPlayer.getValue());
                currentScorePlayer = -1;
                isFirstPlayer = false;
                handWinner = 0;
            }
        }

        myTurn = handWinner != 0;
    }

    public void changeTurn(){
        myTurn = !myTurn;
    }

    public void determineWinner() {
        isOver = true;
        if (scorePlayer > scoreBot) {
            imWinner = true;
            offlineScore.addWin();
        } else if (scorePlayer < scoreBot) {
            offlineScore.addLost();
            imWinner = false;
        } else {
            offlineScore.addTie();
            imWinner = false;
        }
    }

    public void clearTmpArray() {
        cardsOnTheGround.clear();
    }

    public void setPaused() {
        paused = true; 
    }

    public boolean isGameOver() {
        if (isOver){
            return true;
        }
        if (player.getHandPlayer().isEmpty() && bot.getHandBot().isEmpty() && (scorePlayer + scoreBot == 120)) {
            determineWinner();
            return true;
        } else {
            return false;
        }
    }

    public void reset() {
        if (instance != null) {
            instance = null;
            instance = new GameOffline();
        }
    }

    public ArrayList<Card> getDeck() { return deck; }
    public Bot getBot() { return bot; }
    public boolean getIsDealerPlayer() { return isDealerPlayer; }
    public int getScorePlayer() { return scorePlayer; }
    public int getScoreBot() { return scoreBot; }
    public boolean getImWinner() { return imWinner; }
    public ArrayList<Card> getHandBot() {return this.bot.getHandBot();}
    public boolean getPaused() { return paused; }

    public int getCurrentScorePlayer() {return currentScorePlayer;}

    public void setCurrentScorePlayer(int currentScorePlayer) {this.currentScorePlayer = currentScorePlayer;
    }
}
