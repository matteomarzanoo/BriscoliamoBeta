package Model;

import java.io.InputStream;

public class MessageManager {
    private final Game game;
    private Server server;
    private User user;

    public MessageManager(User user, Server server) {
        this.user = user;
        this.server = server;
        this.game = Game.getInstance();
    }

    private boolean isMessageACard(String message) {
        String trimmedMessage = message.trim();
        return trimmedMessage.matches("\\d{1,10}_[OBSC]");
    }

    public void processMessage(String message) {
        try {
            if (message.equals("$")) {
                game.setEndGamePlayer(game.getEndGamePlayer() + 1);
                if (game.getEndGamePlayer() > 1 && game.getHandPlayerOne().isEmpty() && game.getHandPlayerTwo().isEmpty()) {
                    game.setGameOver(true);

                    server.getServerManager().broadcastMessage("&" + game.getScorePlayerOne() + " " + game.getScorePlayerTwo());
                    server.getServerManager().broadcastMessage("+");
                }
                return; // Exit after handling the special message
            }

            // Process the message only if it's the player's turn
            if (server.getServerManager().getClients().indexOf(user) == game.getCurrentTurnIndex()) {
                // We received a card from one of the clients
                if (isMessageACard(message)) {
                    Card card = Card.fromString(message);

                    // Remove the played card from the corresponding client's hand
                    game.getHandPlayerOne().remove(card);
                    game.getHandPlayerTwo().remove(card);

                    if (game.getCardsOnTheGrounds().size() == 2) {
                        game.updateCardsOnTheGrounds();
                        game.getCardsOnTheGrounds().clear(); // Clear the table
                    }

                    // Update cards on the table
                    game.getCardsOnTheGrounds().add(card);
                    game.updateCardsOnTheGrounds();
                    server.getServerManager().broadcastMessage("@" + game.getCardsOnTheGroundsToSend());

                    // Calculate results
                    game.attributeScore();
                }

                // Update hands
                game.updateHands();
                server.getServerManager().sendUpdatedHands();
                server.getServerManager().broadcastMessage("^" + game.getDeck().size());

                //Changing turn
                if(game.getCardsOnTheGrounds().size() == 1)
                {
                    Game.getInstance().nextTurn();
                }
                else if (game.getCardsOnTheGrounds().size() == 2)
                {
                    game.setCurrentTurnIndex(game.getCurrentWinner());
                }

                server.getServerManager().broadcastTurn();
            }
        } catch (Exception e) {
            server.getServerManager().removeUser(user);
        }
    }
}
