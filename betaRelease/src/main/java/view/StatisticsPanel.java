package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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

    private JLabel name;
    private JLabel offWins;
    private JLabel offLost;
    private JLabel offWinrate;
    private JLabel offTies;
    private JLabel totOffGames;
    private JLabel onWins;
    private JLabel onLost;
    private JLabel onTies;
    private JLabel onWinrate;
    private JLabel totOnGames;


    public StatisticsPanel(){
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

    private void addButtonPanel(){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        
        JButton jButton = new JButton("←");
        jButton.setFont(Fonts.getMenuPanel());
        jButton.setOpaque(false);
        jButton.setContentAreaFilled(false);
        jButton.setBorderPainted(false);
        jButton.setFocusPainted(false);
        jButton.setForeground(Color.WHITE);
        jButton.addMouseListener(controller);
        buttonPanel.add(jButton);
        add(buttonPanel, BorderLayout.NORTH);
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

        name(jPanel);
        centerPanel(jPanel);
        add(jPanel, BorderLayout.CENTER);
    }

    private void name(JPanel jPanel){
        name = new JLabel("Welcome back " + String.valueOf(player.getName()) + "!");
        name.setFont(Fonts.getStatisticsPanel());
        name.setForeground(Color.WHITE);
        name.setHorizontalAlignment(SwingConstants.CENTER);
        name.setOpaque(false);
        jPanel.add(name, BorderLayout.NORTH);
    }

    private void centerPanel(JPanel jPanel) {
        JPanel center = new JPanel();
        center.setOpaque(false);
        ArrayList<JLabel> labelList = new ArrayList<>();

        JPanel labelPanel = new JPanel(new GridLayout(12,1,0,0));
        labelPanel.setOpaque(false);

        offWins = new JLabel("Wins: " + String.valueOf(offlineScore.getWins()), SwingConstants.CENTER);
        offWins.setFont(Fonts.getMenuPanel());
        offWins.setForeground(Color.WHITE);
        offWins.setOpaque(false);
        labelList.add(offWins);

        offLost = new JLabel("Lost: " + String.valueOf(offlineScore.getLost()), SwingConstants.CENTER);
        offLost.setFont(Fonts.getMenuPanel());
        offLost.setForeground(Color.WHITE);
        offLost.setOpaque(false);
        labelList.add(offLost);
        
        offTies = new JLabel("Ties: " + String.valueOf(offlineScore.getTies()), SwingConstants.CENTER);
        offTies.setFont(Fonts.getMenuPanel());
        offTies.setForeground(Color.WHITE);
        offTies.setOpaque(false);
        labelList.add(offTies);

        offWinrate = new JLabel("Winrate: " + String.valueOf(offlineScore.getWinrate()) + "%", SwingConstants.CENTER);
        offWinrate.setFont(Fonts.getMenuPanel());
        offWinrate.setForeground(Color.WHITE);
        offWinrate.setOpaque(false);
        labelList.add(offWinrate);

        totOffGames = new JLabel("Total Offline Games: " + String.valueOf(offlineScore.getTotalGames()), SwingConstants.CENTER);
        totOffGames.setFont(Fonts.getMenuPanel());
        totOffGames.setForeground(Color.WHITE);
        totOffGames.setOpaque(false);
        labelList.add(totOffGames);

        onWins = new JLabel("Online Wins: " + String.valueOf(onlineScore.getWins()), SwingConstants.CENTER);
        onWins.setFont(Fonts.getMenuPanel());
        onWins.setForeground(Color.WHITE);
        onWins.setOpaque(false);
        labelList.add(onWins);

        onLost = new JLabel("Online Lost: " + String.valueOf(onlineScore.getLost()), SwingConstants.CENTER);
        onLost.setFont(Fonts.getMenuPanel());
        onLost.setForeground(Color.WHITE);
        onLost.setOpaque(false);
        labelList.add(onLost);

        onTies = new JLabel("Online Ties: " + String.valueOf(onlineScore.getTies()), SwingConstants.CENTER);
        onTies.setFont(Fonts.getMenuPanel());
        onTies.setForeground(Color.WHITE);
        onTies.setOpaque(false);
        labelList.add(onTies);

        onWinrate = new JLabel("Online Winrate: " + String.valueOf(onlineScore.getWinrate()) + "%", SwingConstants.CENTER);
        onWinrate.setFont(Fonts.getMenuPanel());
        onWinrate.setForeground(Color.WHITE);
        onWinrate.setOpaque(false);
        labelList.add(onWinrate);

        totOnGames = new JLabel("Total Online Games: " + String.valueOf(onlineScore.getTotalGames()),  SwingConstants.CENTER);
        totOnGames.setFont(Fonts.getMenuPanel());
        totOnGames.setForeground(Color.WHITE);
        totOnGames.setOpaque(false);
        labelList.add(totOnGames);

        labelPanel.add(new JLabel());
        for(JLabel label : labelList){
            labelPanel.add(label);
        }

        center.add(labelPanel);
        jPanel.add(center);
    }

    public void updateStats() {
        name.setText("Welcome back " + String.valueOf(player.getName()) + "!");
        offWins.setText("Wins: " + String.valueOf(offlineScore.getWins()));
        offLost.setText("Lost: " + String.valueOf(offlineScore.getLost()));
        offTies.setText("Ties: " + String.valueOf(offlineScore.getTies()));
        offWinrate.setText("Winrate: " + String.valueOf(offlineScore.getWinrate()) + "%");
        totOffGames.setText("Total Offline Games: " + String.valueOf(offlineScore.getTotalGames()));
        onWins.setText("Online Wins: " + String.valueOf(onlineScore.getWins()));
        onLost.setText("Online Lost: " + String.valueOf(onlineScore.getLost()));
        onTies.setText("Online Ties: " + String.valueOf(onlineScore.getTies()));
        onWinrate.setText("Online Winrate: " + String.valueOf(onlineScore.getWinrate()) + "%");
        totOnGames.setText("Total Online Games: " + String.valueOf(onlineScore.getTotalGames()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null){ 
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
