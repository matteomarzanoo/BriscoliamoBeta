package Controller;

import Model.Game;
import Model.Server;
import View.ServerPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ServerController implements ActionListener {
    private ServerPanel serverPanel;
    private Server server;

    public ServerController(ServerPanel serverPanel) {
        this.serverPanel = serverPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == serverPanel.getButtonStartListen()) {
            try {
                int port = serverPanel.getPort();
                serverPanel.onServerStart();

                if (server == null) {
                    new Thread(() -> {
                        server = new Server(port);
                        server.start();
                        serverPanel.getLogReader().setServer(server); // Set the server instance in LogReader
                    }).start();

                    new Thread(serverPanel.getLogReader()).start();
                } else {
                    server.getServerManager().getLogs().add("Server already running on port " + port);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(serverPanel, "Invalid port number. Please enter a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
