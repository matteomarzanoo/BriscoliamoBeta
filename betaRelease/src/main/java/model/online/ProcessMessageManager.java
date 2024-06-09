package model.online;

import model.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ProcessMessageManager {
    private GameOnline gameOnline;
    private Client client;

    public ProcessMessageManager() {
        gameOnline = GameOnline.getInstance();
        client = Client.getInstance();
    }

    public void processMessage(String message) {
        switch (message.charAt(0)) {
            case '>':
                if (Objects.equals(message, "game started")) {
                    handleGameStarted();
                } else if (Objects.equals(message, ">")) {
                    handleGameFinished();
                } else {
                    handleNewHand(message.substring(1).trim());
                }
                break;
            case 'Y':
            case 'W':
                if (message.startsWith("YOUR TURN") || message.startsWith("WAIT FOR YOUR TURN")) {
                    handleTurnMessage(message);
                }
                break;
            case '^':
                handleSizeDeck(message.substring(1));
                break;
            case '@':
                handleCardsOnTheGround(message.substring(1));
                break;
            case '-':
                handleBriscola(message.substring(1));
                break;
            case '!':
            case '?':
                handlerHandOpponent(message.substring(1));
                break;
            case '&':
                handleFinalScore(message.substring(1).trim());
                break;
            case '+':
                handleEndGame();
                break;
            default:
                // Handle unexpected messages
                System.out.println("Unexpected message: " + message);
                break;
        }

        System.out.println("gameOnline.isGameOnlineOver() --> " + gameOnline.isGameOnlineOver());

        if (gameOnline.getHandPlayer().isEmpty() && gameOnline.getLengthHandOpponent() == 0 && !gameOnline.isGameOnlineOver()) {
            client.sendMessageToServer("$");
            System.out.println("invio il dollaro come segno che è finita la partita!");
        }
    }

    private void handleEndGame() {
        gameOnline.setGameOnlineOver(true);
        client.getGamePanel().repaint();
    }

    private void handleFinalScore(String message) {
        String[] scoreReceived = message.split(" ");
        ArrayList<String> scores = new ArrayList<>(Arrays.asList(scoreReceived));

        gameOnline.setScorePlayerOne(scores.get(0));
        gameOnline.setScorePlayerTwo(scores.get(1));
    }

    private void handleSizeDeck(String message) {
        gameOnline.setSizeDeck(Integer.parseInt(message));
        System.out.println("size-deck : " + Integer.parseInt(message));
    }

    private void handleGameFinished() {
        System.out.println("partita finita!");
    }

    private void handleGameStarted() {
        System.out.println("Game started!");
    }

    private void handleTurnMessage(String message) {
        boolean yourTurn = message.startsWith("YOUR TURN");
        gameOnline.setMyTurn(yourTurn);
    }

    private void handleNewHand(String handReceived) {
        System.out.println("Updated hand received!");
        gameOnline.getHandPlayer().clear(); // Clear the current hand

        String[] cardsInHand = handReceived.split(" ");
        for (String cardInHand : cardsInHand) {
            gameOnline.getHandPlayer().add(Card.fromString(cardInHand)); // Add each card to the hand
        }
        client.getGamePanel().updatePlayerCardPositions();
    }

    private void handleCardsOnTheGround(String cardsReceived) {
        gameOnline.getCardsOnTheGround().clear();

        if (!cardsReceived.isEmpty()) {
            String[] cardsOnTheGround = cardsReceived.split(" ");
            for (String cardOnTheTable : cardsOnTheGround) {
                gameOnline.getCardsOnTheGround().add(Card.fromString(cardOnTheTable));
            }

            if (gameOnline.getCardsOnTheGround().size() == 2) {
                try {
                    client.getGamePanel().repaint();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
                gameOnline.getCardsOnTheGround().clear();
            }
        }
    }

    private void handleBriscola(String briscolaReceived) {
        System.out.println("briscola: " + briscolaReceived);
        gameOnline.setBriscola(Card.fromString(briscolaReceived));
    }

    private void handlerHandOpponent(String message) {
        gameOnline.setLengthHandOpponent(Integer.parseInt(message));
    }
}
