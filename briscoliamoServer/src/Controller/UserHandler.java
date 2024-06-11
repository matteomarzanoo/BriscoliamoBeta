package Controller;

import Model.*;

import java.io.InputStream;
import java.util.Scanner;

public class UserHandler implements Runnable {
    private final User user;
    private final Server server;
    private final Game game;
    private final MessageManager receivedMessageManager;

    public UserHandler(Server server, User user) {
        this.user = user;
        this.server = server;
        this.game = Game.getInstance();
        this.receivedMessageManager = new MessageManager(this.user, this.server);
    }

    public void run()
    {
        try {
            InputStream clientInput = user.getInputStream();
            Scanner scanner = new Scanner(clientInput);

            while (scanner.hasNextLine()) {
                String receivedMessage = scanner.nextLine();

                receivedMessageManager.processMessage(receivedMessage);
            }
            scanner.close();
        } catch (Exception e) {
            server.getServerManager().removeUser(user);
        }
    }
}
