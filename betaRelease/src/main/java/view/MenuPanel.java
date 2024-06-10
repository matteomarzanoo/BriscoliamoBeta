package view;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import view.online.GamePanelOnline;
import controller.BriscolaController;
import controller.online.BriscolaControllerOnline;
import controller.MainController;
import model.Sound;
import model.online.Client;
import model.Fonts;
import model.ImageManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MenuPanel extends JPanel 
{
    private BufferedImage background;
    private MainController controller;
    private final SettingsPanel settingsPanel;
    private final RulesPanel rulesPanel;
    private final StatisticsPanel statisticsPanel;
    private final ChooseGameType chooseGamePanel;
    private final Sound sound;
    private static MenuPanel instance = null;

    private Client client;
    
    private MenuPanel() {
        background = ImageManager.getBackgroundImage();
        controller = new MainController();
        client = Client.getInstance();
        sound = Sound.getInstance();
        settingsPanel = new SettingsPanel();
        rulesPanel = new RulesPanel();
        statisticsPanel = new StatisticsPanel();
        chooseGamePanel = new ChooseGameType();
        setLayout(new BorderLayout());
        addTitle();
        addButtonsPanel();
        sound.menuOST();
    }

    private void addTitle() {
        JLabel label = new JLabel("Briscoliamo", SwingConstants.CENTER);
        label.setFont(Fonts.getLogo());
        label.setForeground(Color.WHITE);
        label.setOpaque(false);
        add(label, BorderLayout.NORTH);
    }

    private void addButtonsPanel() {
        JPanel jPanel = new JPanel(new GridLayout(5,1));
        jPanel.setOpaque(false);

        String[] nameButtons = {"Play Now!", "Statistics", "Rules", "Settings", "Exit"};
        for (String names : nameButtons) {
            JButton jButton = new JButton(names);
            jButton.setOpaque(false);
            jButton.setContentAreaFilled(false);
            jButton.setBorderPainted(false);
            jButton.setFocusPainted(false);
            jButton.setForeground(Color.WHITE);
            jButton.setFont(Fonts.getMenuPanel());
            jButton.addMouseListener(controller);
            jPanel.add(jButton);
        }
        add(jPanel, BorderLayout.CENTER);
    }

    public void getSelectedPanel(String buttonText) {
        switch (buttonText) {
            case "Play Now!" -> updateView(chooseGamePanel);
            case "Statistics" -> {
                statisticsPanel.updateStats();
                updateView(statisticsPanel);
            }
            case "Rules" -> updateView(rulesPanel);
            case "Settings" -> updateView(settingsPanel);
            case "Exit" -> exit();
            case "←" -> home();
            case "Change Username" -> settingsPanel.UsernameDialog();
            case "Change Settings" -> new ChangeServerConfig();
            case "Home" -> {
                sound.pauseGameOST(); 
                home();
                sound.resetMenuOST();
            }
            case "New Game" -> {
                GamePanelOffline gamePanel = new GamePanelOffline();
                updateView(gamePanel);
                BriscolaController briscolaController = new BriscolaController(gamePanel);
                gamePanel.setController(briscolaController);
            }
            case "Play Offline" -> {
                GamePanelOffline gamePanel = new GamePanelOffline();
                sound.pauseMenuOST();
                updateView(gamePanel);
                sound.gameOST();
                BriscolaController briscolaController = gamePanel.getController();
                gamePanel.setController(briscolaController);

            }
            case "Play Online" -> {
                GamePanelOnline gamePanelOnline = new GamePanelOnline();
                sound.pauseMenuOST();
                updateView(gamePanelOnline);
                sound.gameOST();
                BriscolaControllerOnline briscolaControllerOnline = new BriscolaControllerOnline(gamePanelOnline);
                gamePanelOnline.setController(briscolaControllerOnline);

                client.setConfiguration("127.0.0.1", 8888);
                client.setGamePanel(gamePanelOnline);
                System.out.println("Client start");

                new Thread(client).start();
            }
            case "Rematch" -> {
                GamePanelOnline gamePanelOnline = new GamePanelOnline();
                updateView(gamePanelOnline);
                BriscolaControllerOnline briscolaControllerOnline = new BriscolaControllerOnline(gamePanelOnline);
                gamePanelOnline.setController(briscolaControllerOnline);
            }
        }
    }

    private <T extends Component> void updateView(T panelType) {
        removeAll();
        add(panelType, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void home() {
        removeAll();
        addTitle();
        addButtonsPanel();
        revalidate();
        repaint();
    }

    public void exit() {
        try {
            sound.sfx("hitted_button.wav");
        } catch ( UnsupportedAudioFileException | IOException | LineUnavailableException e ) { e.printStackTrace();}
        int choise = JOptionPane.showConfirmDialog(null, "Do you want to close the game?", "Exit", JOptionPane.YES_NO_OPTION);
        if (choise == JOptionPane.YES_OPTION) {
            System.exit(0); 
        }
    }

    public static MenuPanel getInstance() {
        if ( instance == null ) { instance = new MenuPanel(); }
        return instance;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) { 
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }

}