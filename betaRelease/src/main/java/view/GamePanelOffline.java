package view;

import controller.BriscolaController;
import model.*;
import org.example.Settings;

import javax.swing.*;
import java.awt.*;

public class GamePanelOffline extends GamePanel {
    private BriscolaController controller;
    private GameOffline gameOffline;
    private Bot bot;
    private EndPanel endPanel;
    private static boolean paused;

    public GamePanelOffline() {
        super(GameOffline.getInstance().getPlayer());
        controller = new BriscolaController(this);
        gameOffline = GameOffline.getInstance();
        bot = gameOffline.getBot();
    }

    @Override
    protected void draw(Graphics2D g2d) {
        try {
            drawTempCards(g2d);
            drawBotHand(g2d);
            drawBriscola(g2d);
            drawScores(g2d);
            drawLine(g2d);
            drawPlayerHand(g2d);
            updatePlayerCardPositions();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Processo fallito");
        }
    }

    private void drawBotHand(Graphics2D g2d) {
        for (int i = 0; i < gameOffline.getHandBot().size(); i++) {
            Image cardImage = loadCardImage("/carte/Dorso3.png");
            g2d.drawImage(cardImage, 25 + (25 + 100) * i, 25, Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
        }
    }

    private void drawPlayerHand(Graphics2D g2d) {
        for (Card card : player.getHandPlayer()) {
            Image cardImage = loadCardImage(card.getCardPath());
            if (card.isOver()) {
                int newX = card.getX() - 8;
                int newY = card.getY() - 10;
                int newCardWidth = (int) (Settings.CARD_WIDTH * 1.15);
                int newCardHeight = (int) (Settings.CARD_HEIGHT * 1.15);
                g2d.drawImage(cardImage, newX, newY, newCardWidth, newCardHeight, null);
            } //Gestisco tutti i casi in cui le carte si trovano sui bordi del frame per non poterle portare fuori dal pannello
            //Angolo alto a sinistra
            else if (card.getX()<0 && card.getY()<0){g2d.drawImage(cardImage, 0, 0, Settings.CARD_WIDTH , Settings.CARD_HEIGHT, null);}
            else if (card.getX()<0 && card.getY()<0){g2d.drawImage(cardImage, 0, 0, Settings.CARD_WIDTH , Settings.CARD_HEIGHT, null);}
            //Angolo alto a destra
            else if (card.getX() > Settings.FRAME_WIDTH - Settings.CARD_WIDTH - 15 && card.getY() <0) {g2d.drawImage(cardImage, Settings.FRAME_WIDTH- Settings.CARD_WIDTH - 15, 0, Settings.CARD_WIDTH , Settings.CARD_HEIGHT, null);}
            //Angolo basso a sinistra
            else if (card.getX() <0 && card.getY() > Settings.FRAME_HEIGHT - Settings.CARD_HEIGHT-35 ) {g2d.drawImage(cardImage, 0, Settings.FRAME_HEIGHT - Settings.CARD_HEIGHT -35, Settings.CARD_WIDTH , Settings.CARD_HEIGHT , null);}
            //Angolo basso a destra
            else if(card.getX() > Settings.FRAME_WIDTH - Settings.CARD_WIDTH - 15 && card.getY() > Settings.FRAME_HEIGHT - Settings.CARD_HEIGHT -35){g2d.drawImage(cardImage, Settings.FRAME_WIDTH - Settings.CARD_WIDTH - 15, Settings.FRAME_HEIGHT - Settings.CARD_HEIGHT -35 , Settings.CARD_WIDTH , Settings.CARD_HEIGHT, null);}
            //Bordo Superiore
            else if(card.getY() < 0){g2d.drawImage(cardImage, card.getX(), 0, Settings.CARD_WIDTH , Settings.CARD_HEIGHT , null);}
            //Bordo Sinistro
            else if (card.getX() < 0){g2d.drawImage(cardImage, 0, card.getY(), Settings.CARD_WIDTH , Settings.CARD_HEIGHT , null);}
            //Bordo Destro
            else if(card.getX() > Settings.FRAME_WIDTH - Settings.CARD_WIDTH -15 ){g2d.drawImage(cardImage, Settings.FRAME_WIDTH - Settings.CARD_WIDTH -15, card.getY(), Settings.CARD_WIDTH , Settings.CARD_HEIGHT, null);}
            //Bordo Inferiore
            else if (card.getY()  > Settings.FRAME_HEIGHT - Settings.CARD_HEIGHT -35 ){g2d.drawImage(cardImage, card.getX(), Settings.FRAME_HEIGHT - Settings.CARD_HEIGHT -35 , Settings.CARD_WIDTH , Settings.CARD_HEIGHT, null);}
            //Tutti i casi in cui la carta si trova all'interno del pannello
            else {g2d.drawImage(cardImage, card.getX(), card.getY(), Settings.CARD_WIDTH , Settings.CARD_HEIGHT, null);}
        }
    }

    private void drawBriscola(Graphics2D g) {
        if (!gameOffline.getDeck().isEmpty()) {
            Image bri = loadCardImage(gameOffline.getBriscola().getCardPath());
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.rotate(Math.toRadians(90), Settings.CARD_WIDTH, Settings.CARD_HEIGHT);
            g2d.drawImage(bri, 160, -275, Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
            g2d.dispose();
            Image dorsoBriscola = loadCardImage("/carte/Dorso3.png");
            g.drawImage(dorsoBriscola, 450, 180, Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
        }
    }

    private void drawScores(Graphics2D g2d) {
        g2d.setColor(gameOffline.getImWinner() ? Color.BLACK : Color.WHITE);
        if (!gameOffline.isGameOver()) {
            g2d.setFont(Fonts.getGamePanel());
            g2d.drawString(player.getName() + " : " + gameOffline.getScorePlayer(), 420, 70);
            g2d.drawString(bot.getBotName() + " : " + gameOffline.getScoreBot(), 420, 90);
            g2d.drawString("Carte Rimanenti: " + gameOffline.getDeck().size(), 420, 130);
            g2d.drawString(gameOffline.getIsDealerPlayer() ? "You're the dealer!" : bot.getBotName() + " is the dealer!", 420, 110);
        }
    }

    private void drawTempCards(Graphics g) {
        if (!gameOffline.getCardsOnTheGround().isEmpty()) {
            for (int i = 0; i < gameOffline.getCardsOnTheGround().size(); i++) {
                Card card = gameOffline.getCardsOnTheGround().get(i);
                Image cardImage = loadCardImage(card.getCardPath());
                g.drawImage(cardImage, 75 + 125 * i, 200, Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
            }
        }
    }

    public static void setPaused() { paused = true; }

    @Override
    protected void checkPause() {
        if (paused) {
            int choice = JOptionPane.showConfirmDialog(null, "Do you want to resume the game?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.NO_OPTION) {
                GameOffline.getInstance().reset();
                paused = false;
                Sound.restart(Sound.gameOST);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameOffline.isGameOver()) {
            endPanel = new EndPanel();
            endPanel.setBounds(220, 160, Settings.END_WIDTH, Settings.END_HEIGHT);
            this.add(endPanel);
            GameOffline.getInstance().reset();
        }
    }

    public void setController(BriscolaController controller) {
        this.controller = controller;
        addKeyListener(this.controller);
        addMouseMotionListener(controller.getMouseMotionListener());
        addMouseListener(controller.getMouseListener());
        this.setFocusable(true);
        this.requestFocus();
    }

    public BriscolaController getController() { return this.controller; }

    public Point getPositionCard(Card card) { return card.getPointCard(); }
}