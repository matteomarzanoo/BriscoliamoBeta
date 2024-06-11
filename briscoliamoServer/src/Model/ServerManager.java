package Model;


import java.util.ArrayList;
import java.util.List;

public class ServerManager {
    private List<User> clients;
    private Game game = Game.getInstance();
    private List<String> logs;

    public ServerManager(int port) {
        this.clients = new ArrayList<>();
        this.logs = new ArrayList<>();
    }

    public int getConnectedClients() {
        return clients.size();
    }

    public List<User> getClients() {
        return clients;
    }

    public List<String> getLogs() {
        return logs;
    }

    public void addClient(User user) {
        this.clients.add(user);
    }

    public void removeUser(User user) {
        this.clients.remove(user);
    }

    public void sendUpdatedHands() {
        sendMessageToUser(game.getHandPlayerOneToSend(), clients.get(1));
        sendMessageToUser(game.getHandPlayerTwoToSend(), clients.get(0));

        sendMessageToUser("!" + game.getHandPlayerOne().size(), clients.get(0));
        sendMessageToUser("?" + game.getHandPlayerTwo().size(), clients.get(1));

        broadcastMessage(
                "#" + game.getScorePlayerOne() + " " + game.getScorePlayerTwo()
        );
    }

    public void broadcastMessage(String message) {
        for (User client : this.clients) {
            client.getOutStream().println(message);
        }
    }

    public void sendMessageToUser(String message, User user) {
        for (User client : this.clients) {
            if (client == user) {
                client.getOutStream().println(message);
            }
        }
    }

    public void broadcastTurn() {
        for (int i = 0; i < clients.size(); i++) {
            if (i == game.getCurrentTurnIndex()) {
                sendMessageToUser("YOUR TURN", clients.get(i));
            } else {
                sendMessageToUser("WAIT FOR YOUR TURN", clients.get(i));
            }
        }
    }


}
