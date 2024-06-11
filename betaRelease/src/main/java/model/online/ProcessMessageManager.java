package model.online;

import model.Card;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ProcessMessageManager {
    private final GameOnline gameOnline;
    private final Client client;

    public ProcessMessageManager() {
        this.gameOnline = GameOnline.getInstance();
        this.client = Client.getInstance();
    }

    public void processMessage(String message) {
        if (message.isEmpty()) {
            return;
        }

        char messageType = message.charAt(0);
        String content = message.substring(1).trim();

        switch (messageType) {
            case '>':
                processGameMessage(message, content);
                break;
            case 'Y':
            case 'W':
                handleTurnMessage(message);
                break;
            case '=':
                handleNicknames(content);
                break;
            case '^':
                handleSizeDeck(content);
                break;
            case '@':
                handleCardsOnTheGround(content);
                break;
            case '-':
                handleBriscola(content);
                break;
            case '!':
            case '?':
                handleHandOpponent(content);
                break;
            case '+':
                handleEndGame();
                break;
            case '#':
                handleScorePlayers(content);
                break;
            default:
                // Handle unexpected messages
                System.out.println("Unexpected message: " + message);
                break;
        }

        if (gameOnline.getHandPlayer().isEmpty() && !gameOnline.isGameOnlineOver()) {
            client.sendMessageToServer("$");
            System.out.println("Sent the dollar sign as an indication that the game is over!");
        }
    }

    private void processGameMessage(String message, String content) {
        if (message.equals("> game started")) {
            handleGameStarted();
        } else if (message.equals("> game finished")) {
            handleGameFinished();
        } else {
            handleNewHand(content);
        }
    }

    private void handleScorePlayers(String message) {
        String[] scoresReceived = message.split(" ");
        System.out.println(Arrays.asList(scoresReceived));
        gameOnline.getScoresPlayer().clear();
        gameOnline.getScoresPlayer().add(scoresReceived[0]);
        gameOnline.getScoresPlayer().add(scoresReceived[1]);
    }

    private void handleNicknames(String message) {
        String[] nicknames = message.split(" ");
        gameOnline.getNicknamesPlayer().clear();
        gameOnline.getNicknamesPlayer().addAll(Arrays.asList(nicknames));
    }

    private void handleEndGame() {
        gameOnline.setGameOnlineOver(true);
        client.getGamePanel().repaint();
    }

    private void handleSizeDeck(String message) {
        try {
            int sizeDeck = Integer.parseInt(message);
            gameOnline.setSizeDeck(sizeDeck);
            System.out.println("size-deck: " + sizeDeck);
        } catch (NumberFormatException e) {
            System.out.println("Invalid size deck value: " + message);
        }
    }

    private void handleGameFinished() {
        System.out.println("Game finished!");
    }

    private void handleGameStarted() {
        System.out.println("Game started!");
    }

    private void handleTurnMessage(String message) {
        boolean yourTurn = message.startsWith("YOUR TURN");
        gameOnline.setMyTurn(yourTurn);
    }

    private void handleNewHand(String handReceived) {
        try {
            System.out.println("Updated hand received!");
            gameOnline.getHandPlayer().clear();

            String[] cardsInHand = handReceived.split(" ");
            for (String cardInHand : cardsInHand) {
                gameOnline.getHandPlayer().add(Card.fromString(cardInHand));
            }
            client.getGamePanel().updatePlayerCardPositions();
        } catch (NumberFormatException e) {
            System.out.println("Invalid card format in hand received: " + handReceived);
        }
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
        gameOnline.setBriscola(Card.fromString(briscolaReceived));
    }

    private void handleHandOpponent(String message) {
        try {
            int lengthHandOpponent = Integer.parseInt(message);
            gameOnline.setLengthHandOpponent(lengthHandOpponent);
        } catch (NumberFormatException e) {
            System.out.println("Invalid hand opponent value: " + message);
        }
    }
}
