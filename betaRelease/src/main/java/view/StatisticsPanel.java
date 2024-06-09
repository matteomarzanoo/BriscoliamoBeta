package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import controller.MainController;
import model.Fonts;
import model.ImageManager;
import model.Player;
import model.PlayerScore;

public class StatisticsPanel extends JPanel 
{
    private BufferedImage background;
    private BufferedImage blurredBackground;
    private MainController controller;
    private PlayerScore score;
    private Player player;

    public StatisticsPanel(){
        controller = new MainController();
        background = ImageManager.getBackgroundImage();
        blurredBackground = ImageManager.getBlurred();
        score = PlayerScore.getInstance();
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
        /**
         * Creating panel and add background
         */
        JPanel jPanel = new JPanel(new BorderLayout()){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                if (blurredBackground != null){
                    g.drawImage(blurredBackground, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        /**
         * Adding player's name to the top
         */
        name(jPanel);
        centerPanel(jPanel);
        add(jPanel, BorderLayout.CENTER);
    }

    private void name(JPanel jPanel){
        JLabel label = new JLabel("Welcome back " + player.getName() + "!");
        label.setFont(Fonts.getStatisticsPanel());
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(false);
        jPanel.add(label, BorderLayout.NORTH);
    }

    private void centerPanel(JPanel jPanel) {
        JPanel center = new JPanel();
        center.setOpaque(false);
        ArrayList<JLabel> labelList = new ArrayList<>();

        JPanel labelPanel = new JPanel(new GridLayout(12,1,0,0));
        labelPanel.setOpaque(false);

        JLabel offWins = new JLabel("Wins: " + String.valueOf(score.getWins()), SwingConstants.CENTER);
        offWins.setFont(Fonts.getMenuPanel());
        offWins.setForeground(Color.WHITE);
        offWins.setOpaque(false);
        labelList.add(offWins);

        JLabel offLost = new JLabel("Lost: " + String.valueOf(score.getLost()), SwingConstants.CENTER);
        offLost.setFont(Fonts.getMenuPanel());
        offLost.setForeground(Color.WHITE);
        offLost.setOpaque(false);
        labelList.add(offLost);

        JLabel offWinrate = new JLabel("Winrate: " + String.valueOf(score.getWinrate()) + "%", SwingConstants.CENTER);
        offWinrate.setFont(Fonts.getMenuPanel());
        offWinrate.setForeground(Color.WHITE);
        offWinrate.setOpaque(false);
        labelList.add(offWinrate);

        JLabel offTies = new JLabel("Ties: " + String.valueOf(score.getTies()), SwingConstants.CENTER);
        offTies.setFont(Fonts.getMenuPanel());
        offTies.setForeground(Color.WHITE);
        offTies.setOpaque(false);
        labelList.add(offTies);

        JLabel totOffGames = new JLabel("Total Offline Games: " + String.valueOf(score.getTotalGames()), SwingConstants.CENTER);
        totOffGames.setFont(Fonts.getMenuPanel());
        totOffGames.setForeground(Color.WHITE);
        totOffGames.setOpaque(false);
        labelList.add(totOffGames);

        JLabel onWins = new JLabel("Online Wins: " , SwingConstants.CENTER);
        onWins.setFont(Fonts.getMenuPanel());
        onWins.setForeground(Color.WHITE);
        onWins.setOpaque(false);
        labelList.add(onWins);

        JLabel onLost = new JLabel("Online Lost: " , SwingConstants.CENTER);
        onLost.setFont(Fonts.getMenuPanel());
        onLost.setForeground(Color.WHITE);
        onLost.setOpaque(false);
        labelList.add(onLost);

        JLabel onWinrate = new JLabel("Online Winrate: " + "%", SwingConstants.CENTER);
        onWinrate.setFont(Fonts.getMenuPanel());
        onWinrate.setForeground(Color.WHITE);
        onWinrate.setOpaque(false);
        labelList.add(onWinrate);

        JLabel onTies = new JLabel("Online Ties: ", SwingConstants.CENTER);
        onTies.setFont(Fonts.getMenuPanel());
        onTies.setForeground(Color.WHITE);
        onTies.setOpaque(false);
        labelList.add(onTies);

        JLabel totOnGames = new JLabel("Total Online Games: ",  SwingConstants.CENTER);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null){ 
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
