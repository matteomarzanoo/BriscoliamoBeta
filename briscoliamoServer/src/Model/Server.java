package Model;

import Controller.UserHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    private int port;
    private List<User> clients;
    private Game game = Game.getInstance();
    private ServerManager serverManager;

    public Server(int port) {
        this.port = port;
        this.clients = new ArrayList<>();
        this.serverManager = new ServerManager(port);
    }

    public ServerManager getServerManager() {
        return serverManager;
    }

    public void start() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverManager.getLogs().add("Port " + port + " is now open.");

            while (serverManager.getConnectedClients() < 2) {
                Socket client = serverSocket.accept();

                // get nickname of newUser
                String nickname = (new Scanner( client.getInputStream() )).nextLine();
                nickname = nickname.replace(",", ""); //  ',' use for serialisation
                nickname = nickname.replace(" ", "_");

//                String nickname = String.valueOf(serverManager.getConnectedClients());
                serverManager.getLogs().add("New client " + nickname + " - Host : " + client.getInetAddress().getHostAddress());

                User newUser = new User(client, nickname);
                this.clients.add(newUser);
                serverManager.addClient(newUser);

                executor.execute(new UserHandler(this, newUser));
            }

            game.start();
            serverManager.broadcastMessage("game started");
            serverManager.broadcastTurn();
            serverManager.broadcastMessage("-" + game.getBriscola().toString());
            serverManager.broadcastMessage("=" + clients.getFirst() + " " + clients.get(1));
            serverManager.broadcastMessage(
                    "#" + game.getScorePlayerOne() + " " + game.getScorePlayerTwo()
            );
            serverManager.sendUpdatedHands();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}