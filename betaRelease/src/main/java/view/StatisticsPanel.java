package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import controller.MainController;
import model.Fonts;
import model.ImageManager;
import model.Player;
import model.scores.OfflineScore;
import model.scores.OnlineScore;

public class StatisticsPanel extends JPanel 
{
    private BufferedImage background;
    private BufferedImage blurredBackground;
    private MainController controller;
    private OfflineScore offlineScore;
    private OnlineScore onlineScore;
    private Player player;


    public StatisticsPanel() {
        controller = new MainController();
        background = ImageManager.getBackgroundImage();
        blurredBackground = ImageManager.getBlurred();
        offlineScore = OfflineScore.getInstance();
        onlineScore = OnlineScore.getInstance();
        player = new Player();
        setLayout(new BorderLayout());
        addButtonPanel();
        addStatistics();
    }

    private void addButtonPanel() {        
        JButton jButton = new JButton("←");
        jButton.setFont(Fonts.getMenuPanel());
        jButton.setOpaque(false);
        jButton.setContentAreaFilled(false);
        jButton.setBorderPainted(false);
        jButton.setFocusPainted(false);
        jButton.setForeground(Color.WHITE);
        jButton.setHorizontalAlignment(SwingConstants.LEFT);
        jButton.addMouseListener(controller);
        add(jButton, BorderLayout.NORTH);
    }

    private void addStatistics() {
        JPanel jPanel = new JPanel(new BorderLayout()){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                if (blurredBackground != null){
                    g.drawImage(blurredBackground, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

    JPanel statsPanel = new JPanel(new GridLayout(10, 1, 0, 0));
    statsPanel.setOpaque(false);

    jPanel.add(initLabel("Welcome back ", Integer.toString(offlineScore.getWins()) + "!"), BorderLayout.NORTH);
    statsPanel.add(initLabel("Wins: ", Integer.toString(offlineScore.getWins())));
    statsPanel.add(initLabel("Lost: ", Integer.toString(offlineScore.getLost())));
    statsPanel.add(initLabel("Ties: ", Integer.toString(offlineScore.getTies())));
    statsPanel.add(initLabel("Winrate %: ", Integer.toString(offlineScore.getWinrate())));
    statsPanel.add(initLabel("Total Offline Games: ", Integer.toString(offlineScore.getTotalGames())));
    statsPanel.add(initLabel("Online Wins: ", Integer.toString(onlineScore.getWins())));
    statsPanel.add(initLabel("Online Lost: ", Integer.toString(onlineScore.getLost())));
    statsPanel.add(initLabel("Online Ties: ", Integer.toString(onlineScore.getTies())));
    statsPanel.add(initLabel("Online Winrate %: ", Integer.toString(onlineScore.getWinrate())));
    statsPanel.add(initLabel("Total Online Games: ", Integer.toString(onlineScore.getTotalGames())));

    jPanel.add(statsPanel, BorderLayout.CENTER);    
    add(jPanel, BorderLayout.CENTER);
    }

    private JLabel initLabel(String name, String score) {
        JLabel label = new JLabel();
        label.setFont(Fonts.getMenuPanel());
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(false);

        if (name.equals("Welcome back ")){
            label.setText(name + player.getNickname());
            label.setFont(Fonts.getStatisticsPanel());
        } else {
            label.setText(name + String.valueOf(score));
        }
        return label;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null){ 
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
