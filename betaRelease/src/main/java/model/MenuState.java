package model;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

import controller.BriscolaController;
import controller.online.BriscolaControllerOnline;
import model.online.Client;
import view.*;
import view.online.GamePanelOnline;

public class MenuState {
    private final Sound sound;
    private final ChooseGameType chooseGamePanel;
    private final RulesPanel rulesPanel;
    private final SettingsPanel settingsPanel;
    private final Client client;

    public MenuState(){
        this.client = Client.getInstance();
        this.sound = Sound.getInstance();
        this.settingsPanel = new SettingsPanel();
        this.rulesPanel = new RulesPanel();
        this.chooseGamePanel = new ChooseGameType();
        sound.menuOST();
    }

    public void getSelectedPanel(String buttonText) {
        switch (buttonText) {
            case "Play Now!" -> MenuPanel.getInstance().updateView(chooseGamePanel);
            case "Statistics" -> MenuPanel.getInstance().updateView(new StatisticsPanel());
            case "Rules" -> MenuPanel.getInstance().updateView(rulesPanel);
            case "Settings" -> MenuPanel.getInstance().updateView(settingsPanel);
            case "Change Username" -> settingsPanel.UsernameDialog();
            case "Change Settings" -> new ChangeServerConfig();
            case "Home" -> {
                sound.pauseGameOST(); 
                home();
                sound.resetMenuOST();
            }
            case "New Game" -> {
                GamePanelOffline gamePanel = new GamePanelOffline();
                MenuPanel.getInstance().updateView(gamePanel);
                BriscolaController briscolaController = new BriscolaController(gamePanel);
                gamePanel.setController(briscolaController);
            }
            case "Play Offline" -> {
                GamePanelOffline gamePanel = new GamePanelOffline();
                sound.pauseMenuOST();
                MenuPanel.getInstance().updateView(gamePanel);
                sound.gameOST();
                BriscolaController briscolaController = gamePanel.getController();
                gamePanel.setController(briscolaController);

            }
            case "Play Online" -> {
                GamePanelOnline gamePanelOnline = new GamePanelOnline();
                sound.pauseMenuOST();
                MenuPanel.getInstance().updateView(gamePanelOnline);
                sound.gameOST();
                BriscolaControllerOnline briscolaControllerOnline = new BriscolaControllerOnline(gamePanelOnline);
                gamePanelOnline.setController(briscolaControllerOnline);

                client.setConfiguration(client.getHost(), client.getPort());
                client.setGamePanel(gamePanelOnline);

                new Thread(client).start();
            }
            case "Rematch" -> {
                home();
            }
            case "Exit" -> exit();
            case "←" -> home();
        }
    }

    public void home() {
        MenuPanel.getInstance().removeAll();
        MenuPanel.getInstance().addTitle();
        MenuPanel.getInstance().addButtonsPanel();
        MenuPanel.getInstance().revalidate();
        MenuPanel.getInstance().repaint();
    }

    public void exit() {
        try {
            sound.sfx("hitted_button.wav");
        } catch ( UnsupportedAudioFileException | IOException | LineUnavailableException e ) {}
        int choise = JOptionPane.showConfirmDialog(null, "Do you want to close the game?", "Exit", JOptionPane.YES_NO_OPTION);
        if (choise == JOptionPane.YES_OPTION) {
            System.exit(0); 
        }
    }
}
