package Model;

import java.util.*;

public class Game
{
    private static Game instance = null;
    private final List<Card> deck;
    private final List<Card> cardsOnTheGrounds;
    private final List<Card> handPlayerOne;
    private final List<Card> handPlayerTwo;
    private Card briscola;
    private int currentTurnIndex;
    private int vincitoreManoPrecedente;
    private Map<Integer, Integer> valuesMap;
    private StringBuilder handPlayerOneToSend;
    private StringBuilder handPlayerTwoToSend;
    private StringBuilder cardsOnTheGroundsToSend;
    private Card playedCardPlayerOne;
    private Card playedCardPlayerTwo;
    private int scorePlayerOne;
    private int scorePlayerTwo;
    private int currentWinner;
    private int endGamePlayer = 0;
    private boolean gameOver = false;

    private final Random random = new Random();

    private Game()
    {
        deck = new ArrayList<>();
        cardsOnTheGrounds = new ArrayList<>(2);
        handPlayerOne = new ArrayList<>(3);
        handPlayerTwo = new ArrayList<>(3);
        valuesMap = initValuesMap();
    }

    public static Game getInstance()
    {
        if (instance == null)
        {
            instance = new Game();
        }
        return instance;
    }

    public int getEndGamePlayer() {
        return endGamePlayer;
    }

    public void setEndGamePlayer(int endGamePlayer) {
        this.endGamePlayer = endGamePlayer;
    }

    public int getScorePlayerOne() {
        return scorePlayerOne;
    }

    public int getScorePlayerTwo() {
        return scorePlayerTwo;
    }

    public synchronized void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    public synchronized boolean isGameOver() {
        return gameOver;
    }

    public int getCurrentWinner() {
        return currentWinner;
    }

    public int getCurrentTurnIndex()
    {
        return currentTurnIndex;
    }

    public void setCurrentTurnIndex(int currentTurnIndex)
    {
        this.currentTurnIndex = currentTurnIndex;
    }

    public List<Card> getHandPlayerOne()
    {
        return handPlayerOne;
    }

    public List<Card> getHandPlayerTwo()
    {
        return handPlayerTwo;
    }

    public String getCardsOnTheGroundsToSend()
    {
        return cardsOnTheGroundsToSend.toString();
    }

    public String getHandPlayerOneToSend()
    {
        return handPlayerOneToSend.toString();
    }

    public String getHandPlayerTwoToSend()
    {
        return handPlayerTwoToSend.toString();
    }

    public Card getBriscola()
    {
        return briscola;
    }

    public List<Card> getDeck()
    {
        return deck;
    }

    public List<Card> getCardsOnTheGrounds()
    {
        return cardsOnTheGrounds;
    }

    private void initializeDeck()
    {
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

    private Card chooseBriscola()
    {
        return deck.getLast();
    }

    private Map<Integer, Integer> initValuesMap()
    {
        Map<Integer, Integer> cardValues = new HashMap<>();

        cardValues.put(1, 11);
        cardValues.put(2, 0);
        cardValues.put(3, 10);
        cardValues.put(4, 0);
        cardValues.put(5, 0);
        cardValues.put(6, 0);
        cardValues.put(7, 0);
        cardValues.put(8, 2);
        cardValues.put(9, 3);
        cardValues.put(10, 4);

        return cardValues;
    }

    private int chooseDealer()
    {
        return random.nextInt(2);
    }

    private void initHands()
    {
        for (int i = 0; i < 3; i++)
        {
            handPlayerOne.add(deck.removeLast());
            handPlayerTwo.add(deck.removeLast());
        }
    }

    public void updateHands()
    {
        handPlayerOneToSend = new StringBuilder(">");
        handPlayerTwoToSend = new StringBuilder(">");

        handPlayerOne.forEach(card -> handPlayerOneToSend.append(card).append(" "));
        handPlayerTwo.forEach(card -> handPlayerTwoToSend.append(card).append(" "));
    }

    public void updateCardsOnTheGrounds()
    {
        cardsOnTheGroundsToSend = new StringBuilder();
        cardsOnTheGrounds.forEach(card -> cardsOnTheGroundsToSend.append(card).append(" "));
    }

    public void attributeScore()
    {
        if (cardsOnTheGrounds.size() == 2)
        {
            if (vincitoreManoPrecedente == 0)
            {
                playedCardPlayerOne = cardsOnTheGrounds.get(0);
                playedCardPlayerTwo = cardsOnTheGrounds.get(1);
            }
            else
            {
                playedCardPlayerOne = cardsOnTheGrounds.get(1);
                playedCardPlayerTwo = cardsOnTheGrounds.get(0);
            }
            calculateScore(playedCardPlayerOne, playedCardPlayerTwo);
        }
    }

    public void calculateScore(Card playedCardPlayerOne, Card playedCardPlayerTwo)
    {
        String suitPlayerOne = playedCardPlayerOne.getSuit();
        String suitPlayerTwo = playedCardPlayerTwo.getSuit();

        int valuePlayerOne = playedCardPlayerOne.getValue();
        int valuePlayerTwo = playedCardPlayerTwo.getValue();

        boolean sameSuit = suitPlayerOne.equals(suitPlayerTwo);
        boolean playerOneIsBriscola = suitPlayerOne.equals(briscola.getSuit());
        boolean playerTwoIsBriscola = suitPlayerTwo.equals(briscola.getSuit());

        int scoreIncrement = valuesMap.get(valuePlayerOne) + valuesMap.get(valuePlayerTwo);

        if (sameSuit)
        {
            if (valuesMap.get(valuePlayerOne) > valuesMap.get(valuePlayerTwo))
            {
                scorePlayerOne += scoreIncrement;
                currentWinner = 0;
            }
            else
            {
                scorePlayerTwo += scoreIncrement;
                currentWinner = 1;
            }
        }
        else if (playerOneIsBriscola)
        {
            scorePlayerOne += scoreIncrement;
            currentWinner = 0;
        }
        else if (playerTwoIsBriscola)
        {
            scorePlayerTwo += scoreIncrement;
            currentWinner = 1;
        }
        else
        {
            if (vincitoreManoPrecedente == 0)
            {
                scorePlayerOne += scoreIncrement;
                currentWinner = 0;
            }
            else
            {
                scorePlayerTwo += scoreIncrement;
                currentWinner = 1;
            }
        }

        if (!deck.isEmpty())
        {
            handPlayerOne.add(deck.removeFirst());
            handPlayerTwo.add(deck.removeFirst());
        }

        // Imposta il giocatore vincitore come il primo a giocare nella mano successiva
        vincitoreManoPrecedente = currentWinner;
    }

    public void start()
    {
        initializeDeck();
        currentTurnIndex = ((chooseDealer() + 1) % 2);
        initHands();
        updateHands();
        briscola = chooseBriscola();
    }

    public void nextTurn() {
        currentTurnIndex = (currentTurnIndex + 1) % 2;
    }
}
