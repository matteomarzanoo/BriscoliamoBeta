package controller.online;

import model.*;
import model.online.*;
import view.online.*;

import javax.swing.*;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

public class ReceivedMessagesHandler implements Runnable
{
    private InputStream server;
    private GamePanelOnline gamePanel;
    private GameOnline game;
    private ProcessMessageManager processMessageManager;

    public ReceivedMessagesHandler(InputStream server, GamePanelOnline gamePanel)
    {
        this.server = server;
        this.gamePanel = gamePanel;
        this.game = GameOnline.getInstance();
        processMessageManager = new ProcessMessageManager();
    }

    @Override
    public void run()
    {
        try (Scanner scanner = new Scanner(server))
        {
            while (scanner.hasNextLine() && !game.isGameOnlineOver())
            {
                String messageReceived = scanner.nextLine();
                processMessageManager.processMessage(messageReceived);
                gamePanel.repaint();
            }
        }
    }
}
