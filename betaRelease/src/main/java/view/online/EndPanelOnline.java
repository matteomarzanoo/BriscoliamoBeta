package view.online;

import controller.MainController;
import model.online.GameOnline;
import org.example.Settings;

import javax.swing.*;
import java.awt.*;

public class EndPanelOnline extends JPanel {

    private GameOnline game;
    private MainController controller;

    public EndPanelOnline()
    {
        this.game = GameOnline.getInstance();
        controller = new MainController();

        this.setSize(Settings.END_WIDTH, Settings.END_HEIGHT);
        this.setBackground(new Color(0, 0, 0));

        if (game.isGameOnlineOver()) {
            game.getCardsOnTheGround().clear();
            //if (game.getImWinner()) {
            if (true) {
                this.setBorder(BorderFactory.createLineBorder(new Color(0xe8d107), 7));
            } else {
                this.setBorder(BorderFactory.createLineBorder(new Color(0xFFC700), 7));
            }
        }
        addLabelsToContentPanel(this);
        addButtonsToPanel(this);

        setVisible(true);
        setFocusable(true);
        repaint();


    }
    private void addLabelsToContentPanel(JPanel panel) {
        printScore(panel);
        printFirstPlayer(panel);
        printSecondPlayer(panel);
        printScores(panel);
    }


    private void addButtonsToPanel(JPanel panel) 
    {
        JButton continua = new JButton("Home");
        continua.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        continua.setBounds(Settings.END_WIDTH / 2 - 140, Settings.END_HEIGHT - 100, 120, 40);
        continua.setBorder(BorderFactory.createLineBorder(new Color(0xFFE8D107), 2));
        continua.addMouseListener(controller);

        panel.add(continua);

        JButton back= new JButton("Rematch");
        back.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        back.setBounds(Settings.END_WIDTH / 2 + 15, Settings.END_HEIGHT - 100, 120, 40);
        back.setBorder(BorderFactory.createLineBorder(new Color(0xe8d107), 2));
        back.addMouseListener(controller);

        panel.add(back);
    }

    private void printScores(JPanel panel)
    {
        System.out.println("aggiorno punti finali");
        JLabel score1 = new JLabel(game.getScorePlayerOne(), SwingConstants.CENTER);
        JLabel score2 = new JLabel(game.getScorePlayerTwo(), SwingConstants.CENTER);

        score1.setFont(new Font("Times New Roman", Font.PLAIN, 17));
        score2.setFont(new Font("Times New Roman", Font.PLAIN, 17));

        score1.setBounds(27, 60, Settings.END_WIDTH - 230, 70);
        score1.setForeground(Color.WHITE);
        score2.setBounds(200, 60, Settings.END_WIDTH - 230, 70);
        score2.setForeground(Color.WHITE);

        panel.add(score1);
        panel.add(score2);

    }
    private void printSecondPlayer(JPanel panel) {
        JLabel nameB = new JLabel("Player 2", SwingConstants.CENTER);
        nameB.setFont(new Font("Times New Roman", Font.PLAIN, 17));
        nameB.setBounds(200, 30, Settings.END_WIDTH - 230, 70);
        nameB.setForeground(Color.WHITE);
        panel.add(nameB);

    }
    private void printFirstPlayer(JPanel panel) {
        JLabel nameP = new JLabel("Player 1", SwingConstants.CENTER);
        nameP.setFont(new Font("Times New Roman", Font.PLAIN, 17));
        nameP.setBounds(10, 30, Settings.END_WIDTH - 200, 70);
        nameP.setForeground(Color.WHITE);
        panel.add(nameP);

    }
    private void printScore(JPanel panel) {
        JLabel punteggio = new JLabel("FINAL SCORE", SwingConstants.CENTER);
        punteggio.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        punteggio.setBounds(50, 10, Settings.END_WIDTH - 100, 50);
        punteggio.setForeground( new Color(0xe8d107));
        panel.add(punteggio);
    }

    private void drawLine(Graphics2D g2d){
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color color = new Color(0xe8d107);
        Color transparentGold = new Color(0xe8d107 & 0x00FFFFFF, true);

        int width = Settings.END_WIDTH;
        int height = Settings.END_HEIGHT;

        GradientPaint inizio = new GradientPaint(0, height / 4, transparentGold, width / 4, height / 2, color, false);
        GradientPaint fine = new GradientPaint(width * 3 / 4, height / 2, color, width, height / 2, transparentGold, false);

        g2d.setPaint(inizio);
        g2d.drawLine(0, height / 2, width/4, height / 2);

        g2d.setPaint(fine);
        g2d.drawLine(width * 3 / 4, height / 2, width, height / 2);
        g2d.drawLine(88, height / 2, 261, height / 2);
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.WHITE);
        g.drawLine(40, 50, 310, 50);
        drawLine(g2d);
    }
}


