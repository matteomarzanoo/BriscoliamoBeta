package model.online;

import controller.online.ReceivedMessagesHandler;
import view.online.GamePanelOnline;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable
{
    private String host;
    private int port;
    private Socket clientSocket;
    private PrintStream output;
    private GamePanelOnline gamePanel;
    private ReceivedMessagesHandler receivedMessagesHandler;

    public static Client instance = null;

    public Client() {}

    public static Client getInstance()
    {
        if(instance == null)
        {
            instance = new Client();
        }
        return instance;
    }

    public GamePanelOnline getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(GamePanelOnline gamePanel) {
        this.gamePanel = gamePanel;
    }

    public ReceivedMessagesHandler getReceivedMessagesHandler()
    {
        return receivedMessagesHandler;
    }

    public void setConfiguration(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    public void sendMessageToServer(String message)
    {
        if(output != null)
        {
            output.println(message);
        }
        else
        {
            System.err.println("Output stream is not initialized.");
        }
    }

    @Override
    public void run()
    {
        try
        {
            clientSocket = new Socket(host, port);
            receivedMessagesHandler = new ReceivedMessagesHandler(clientSocket.getInputStream(), gamePanel);
            System.out.println("Client successfully connected to server!");

            output = new PrintStream(clientSocket.getOutputStream());

            Scanner sc = new Scanner(System.in);

            new Thread(receivedMessagesHandler).start();

            while (sc.hasNextLine())
            {
                String message = sc.nextLine();
                if (message.equalsIgnoreCase("exit"))
                {
                    break;
                }
                output.println(message);
            }

            output.close();
            sc.close();
            disconnect();
            System.out.println("Client disconnected.");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void disconnect()
    {
        try
        {
            if (clientSocket != null)
            {
                clientSocket.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
