package View;

import Model.Game;
import Model.Server;

import javax.swing.*;
import java.util.List;

public class LogReader implements Runnable {
    private Server server;
    private DefaultListModel<String> listModel;
    private Game game;

    public LogReader(JList<String> listLogs, DefaultListModel<String> listModel) {
        this.listModel = listModel;
        this.game = Game.getInstance();
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void updateLogList(List<String> logs) {
        SwingUtilities.invokeLater(() -> {
            listModel.clear();
            for (String log : logs) {
                listModel.addElement(log);
            }
        });
    }

    @Override
    public void run() {
        while (!game.isGameOver()) { // Assuming `isGameOver` returns true when the game is finished
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (server != null) {
                updateLogList(server.getServerManager().getLogs());
            }
        }
    }
}
