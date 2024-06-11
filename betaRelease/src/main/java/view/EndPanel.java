package view;

import model.GameOffline;
import model.ImageManager;

import org.example.Settings;

import controller.MainController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class EndPanel extends JPanel {

    private GameOffline game;
    private MainController controller;
    private BufferedImage background;

    public EndPanel()
    {
        this.game = GameOffline.getInstance();
        controller = new MainController();
        background =  ImageManager.getEndPanelBackground();
        this.setSize(Settings.END_WIDTH, Settings.END_HEIGHT);

        if (game.isGameOver()) {
            game.clearTmpArray();
            Border one = new LineBorder(new Color(140, 103, 3), 1);
            Border two = new LineBorder(Color.BLACK, 7);
            Border union = new CompoundBorder(two,one);
            this.setBorder(union);
        }
        Color gold = new Color(232,209,7);

        addLabelsToContentPanel(this, gold);
        addButtonsToPanel(this, gold);

        setVisible(true);
        setFocusable(true);
        repaint();

    }

    private void addLabelsToContentPanel(JPanel panel, Color gold) {
        printScore(panel);
        printNamePlayer(panel);
        printNameBot(panel);
        printScores(panel, gold);
    }


    private void addButtonsToPanel(JPanel panel, Color gold)
    {
        JButton home = new JButton("Home");
        home.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        home.setBounds(Settings.END_WIDTH / 2 - 140, Settings.END_HEIGHT - 65, 120, 40);
        home.setOpaque(false);
        home.setForeground(Color.WHITE);
        home.setContentAreaFilled(false);
        home.setFocusPainted(false);
        Border one = new LineBorder(gold, 2);
        Border two = new LineBorder(Color.BLACK, 3);
        Border union = new CompoundBorder(two,one);
        home.setBorder(union);
        home.addMouseListener(controller);

        panel.add(home);

        JButton playAgain = new JButton("New Game");
        playAgain.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        playAgain.setBounds(Settings.END_WIDTH / 2 + 25, Settings.END_HEIGHT - 65, 120, 40);
        playAgain.setOpaque(false);
        playAgain.setForeground(Color.WHITE);
        playAgain.setContentAreaFilled(false);
        playAgain.setFocusPainted(false);
        Border one1 = new LineBorder(gold, 2);
        Border two2 = new LineBorder(Color.BLACK, 3);
        Border union3 = new CompoundBorder(two2,one1);
        playAgain.setBorder(union3);
        playAgain.addMouseListener(controller);

        panel.add(playAgain);
    }

    private void printScores(JPanel panel, Color gold){

        JLabel score1 = new JLabel(String.valueOf(game.getScorePlayer()), SwingConstants.CENTER);
        JLabel score2 = new JLabel(String.valueOf(game.getScoreBot()), SwingConstants.CENTER);

        score1.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        score2.setFont(new Font("Times New Roman", Font.PLAIN, 25));

        score1.setBounds(27, 75, Settings.END_WIDTH - 230, 70);
        score1.setForeground(gold);
        score2.setBounds(200, 75, Settings.END_WIDTH - 230, 70);
        score2.setForeground(gold);

        panel.add(score1);
        panel.add(score2);

    }
    private void printNameBot(JPanel panel){
        JLabel nameB = new JLabel(game.getBot().getBotName(), SwingConstants.CENTER);
        nameB.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        nameB.setBounds(200, 40, Settings.END_WIDTH - 230, 70);
        nameB.setForeground(Color.BLACK);
        panel.add(nameB);

    }
    private void printNamePlayer(JPanel panel){
        JLabel nameP = new JLabel(game.getPlayer().getNickname(), SwingConstants.CENTER);
        nameP.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        nameP.setBounds(10, 40, Settings.END_WIDTH - 200, 70);
        nameP.setForeground(Color.BLACK);
        panel.add(nameP);

    }
    private void printScore(JPanel panel){
        JLabel punteggio = new JLabel("FINAL SCORE", SwingConstants.CENTER);
        punteggio.setFont(new Font("Times New Roman", Font.PLAIN, 26));
        punteggio.setBounds(50, 7, Settings.END_WIDTH - 100, 50);
        punteggio.setForeground(Color.BLACK);
        panel.add(punteggio);
    }
    private void drawLine(Graphics2D g2d, int yPosition){
        Color gold = new Color(232,209,7);
        Color goldSfumato = new Color(250,250,250,0);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int centerX = width / 2;

        GradientPaint left = new GradientPaint(centerX, yPosition, gold, 0, yPosition, goldSfumato, false);
        g2d.setPaint(left);
        g2d.drawLine(centerX, yPosition, 0, yPosition);

        GradientPaint right = new GradientPaint(centerX, yPosition, gold, width, yPosition, goldSfumato, false);
        g2d.setPaint(right);
        g2d.drawLine(centerX, yPosition, width, yPosition);}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.WHITE);

        drawLine(g2d, 50);
        drawLine(g2d, 175);
    }
}