package view.online;

import model.ImageManager;
import model.online.GameOnline;
import org.example.Settings;
import controller.MainController;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class EndPanelOnline extends JPanel {

    private final GameOnline game;
    private final MainController controller;
    private final BufferedImage background;

    public EndPanelOnline() {
        this.game = GameOnline.getInstance();
        this.controller = new MainController();
        this.background = ImageManager.getEndPanelBackground();
        this.setSize(Settings.END_WIDTH, Settings.END_HEIGHT);

        if (game.isGameOnlineOver()) {
            game.getCardsOnTheGround().clear();

            Border compoundBorder = new CompoundBorder(
                    new LineBorder(Color.BLACK, 7),
                    new LineBorder(new Color(140, 103, 3), 1)
            );
            this.setBorder(compoundBorder);

            this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 7));
        }

        Color gold = new Color(232, 209, 7);
        addLabelsToContentPanel(this, gold);
        addButtonsToPanel(this, gold);

        setVisible(true);
        setFocusable(true);
        repaint();
    }

    private void addLabelsToContentPanel(JPanel panel, Color gold) {
        printScore(panel);
        printNamePlayer(panel);
        printNameEnemy(panel);
        printScores(panel, gold);
    }

    private void addButtonsToPanel(JPanel panel, Color gold) {
        JButton home = new JButton("Home");
        home.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        home.setBounds(Settings.END_WIDTH / 2 - 140, Settings.END_HEIGHT - 65, 120, 40);
        home.setOpaque(false);
        home.setContentAreaFilled(false);
        home.setFocusPainted(false);
        Border one = new LineBorder(gold, 2);
        Border two = new LineBorder(Color.BLACK, 3);
        Border union = new CompoundBorder(two, one);
        home.setBorder(union);
        home.addMouseListener(controller);

        panel.add(home);

        JButton playAgain = new JButton("New Game");
        playAgain.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        playAgain.setBounds(Settings.END_WIDTH / 2 + 25, Settings.END_HEIGHT - 65, 120, 40);
        playAgain.setOpaque(false);
        playAgain.setContentAreaFilled(false);
        playAgain.setFocusPainted(false);
        Border one1 = new LineBorder(gold, 2);
        Border two2 = new LineBorder(Color.BLACK, 3);
        Border union3 = new CompoundBorder(two2, one1);
        playAgain.setBorder(union3);
        playAgain.addMouseListener(controller);

        panel.add(playAgain);
    }

    private void printScores(JPanel panel, Color gold) {
        List<String> scoresPlayer = game.getScoresPlayer();

        JLabel score1 = new JLabel(scoresPlayer.get(0), SwingConstants.CENTER);
        JLabel score2 = new JLabel(scoresPlayer.get(1), SwingConstants.CENTER);

        score1.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        score2.setFont(new Font("Times New Roman", Font.PLAIN, 25));

        score1.setBounds(27, 75, Settings.END_WIDTH - 230, 70);
        score1.setForeground(gold);
        score2.setBounds(200, 75, Settings.END_WIDTH - 230, 70);
        score2.setForeground(gold);

        panel.add(score1);
        panel.add(score2);
    }

    private void printNameEnemy(JPanel panel) {
        List<String> nicknamesPlayer = game.getNicknamesPlayer();

        JLabel nameB = new JLabel(nicknamesPlayer.get(1), SwingConstants.CENTER);
        nameB.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        nameB.setBounds(200, 40, Settings.END_WIDTH - 230, 70);
        nameB.setForeground(Color.BLACK);
        panel.add(nameB);
    }

    private void printNamePlayer(JPanel panel) {
        JLabel nameP = new JLabel(game.getPlayer().getNickname(), SwingConstants.CENTER);
        nameP.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        nameP.setBounds(10, 40, Settings.END_WIDTH - 200, 70);
        nameP.setForeground(Color.BLACK);
        panel.add(nameP);
    }

    private void printScore(JPanel panel) {
        JLabel finalScore = new JLabel("FINAL SCORE", SwingConstants.CENTER);
        finalScore.setFont(new Font("Times New Roman", Font.PLAIN, 26));
        finalScore.setBounds(50, 7, Settings.END_WIDTH - 100, 50);
        finalScore.setForeground(Color.BLACK);
        panel.add(finalScore);
    }

    private void drawLine(Graphics2D g2d, int yPosition) {
        Color gold = new Color(232, 209, 7);
        Color goldSfumato = new Color(250, 250, 250, 0);
        int width = getWidth();
        int centerX = width / 2;

        GradientPaint gradientLeft = new GradientPaint(centerX, yPosition, gold, 0, yPosition, goldSfumato, false);
        g2d.setPaint(gradientLeft);
        g2d.drawLine(centerX, yPosition, 0, yPosition);

        GradientPaint gradientRight = new GradientPaint(centerX, yPosition, gold, width, yPosition, goldSfumato, false);
        g2d.setPaint(gradientRight);
        g2d.drawLine(centerX, yPosition, width, yPosition);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
        Graphics2D g2d = (Graphics2D) g;
        drawLine(g2d, 50);
        drawLine(g2d, 175);
    }
}