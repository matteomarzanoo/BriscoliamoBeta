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
    private Sound sound;

    public GamePanelOffline() {
        super(GameOffline.getInstance().getPlayer());
        controller = new BriscolaController(this);
        gameOffline = GameOffline.getInstance();
        bot = gameOffline.getBot();
        sound = Sound.getInstance();
        checkPause();
    }

    @Override
    protected void draw(Graphics2D g2d) {
        try {
            drawCardsOnTheGround(g2d);
            drawBotHand(g2d);
            drawBriscolaAndDeck(g2d);
            drawScores(g2d);
            drawPlayerHand(g2d);
            updatePlayerCardPositions();
           if (gameOffline.getCurrentScorePlayer() > 0){
               drawRectangle(g2d);
           }
        } catch (Exception e) {}

    }
    private void drawBotHand(Graphics2D g2d) {
        for (int i = 0; i < gameOffline.getHandBot().size(); i++) {
            Image cardImage = loadCardImage("/carte/Dorso3.png");
            g2d.drawImage(cardImage, 150 + 125 * i, 25, Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
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
            }
            //Angolo alto a sinistra
            else if (card.getX()<0 && card.getY()<0){g2d.drawImage(cardImage, 0, 0, Settings.CARD_WIDTH , Settings.CARD_HEIGHT, null);}
            //Anglo alto a destra
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

    private void drawBriscolaAndDeck(Graphics2D g) {
        if (!gameOffline.getDeck().isEmpty()) {
            Image bri = loadCardImage(gameOffline.getBriscola().getCardPath());
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.rotate(Math.toRadians(270), (int)(Settings.CARD_WIDTH /2), (int) (Settings.CARD_HEIGHT/2));
            g2d.drawImage(bri, -180, 580, Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
            g2d.dispose();
            Image dorsoBriscola = loadCardImage("/carte/Dorso3.png");
            g.drawImage(dorsoBriscola, 650, 180, Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
        }
    }

    private void drawScores(Graphics2D g2d) {
        g2d.setColor(gameOffline.getImWinner() ? Color.BLACK : Color.WHITE);
        if (!gameOffline.isGameOver()) {
            g2d.setFont(Fonts.getGamePanel());
            g2d.drawString(bot.getBotName() + " : " + gameOffline.getScoreBot(), 565, 450);
            g2d.drawString(player.getNickname() + " : " + gameOffline.getScorePlayer(), 565, 480);

            if(! gameOffline.getDeck().isEmpty()) {
                g2d.drawString("" + gameOffline.getDeck().size(), 700, 360);
            }

            if(gameOffline.isMyTurn()){
                g2d.drawString("It's your turn", 565, 40);
            }else{
                g2d.drawString("It's "+ bot.getBotName() + " turn", 565, 40);
            }
        }
    }

    private void drawCardsOnTheGround(Graphics g) {
        if (!gameOffline.getCardsOnTheGround().isEmpty()) {
            for (int i = 0; i < gameOffline.getCardsOnTheGround().size(); i++) {
                Card card = gameOffline.getCardsOnTheGround().get(i);
                Image cardImage = loadCardImage(card.getCardPath());
                g.drawImage(cardImage, 200 + 150 * i, 204, Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
            }
        }
    }

    private void drawRectangle(Graphics2D g2d){
        int x = (int) (Settings.FRAME_WIDTH  - 250 ) / 2;
        int y = (int) (Settings.FRAME_HEIGHT - 50) / 2;
        int width = 100;
        int height = 50;
        int arcWidth = 50;
        int arcHeight = 50;
        int borderWidth = 5;
        String currentScorePlayer = "+ " + GameOffline.getInstance().getCurrentScorePlayer() + " points";

        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(x, y, width, height, arcWidth, arcHeight);

        g2d.setColor(Color.GREEN);
        g2d.setStroke(new BasicStroke(borderWidth));
        g2d.drawRoundRect(x, y, width, height, arcWidth, arcHeight);

        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g2d.getFontMetrics();
        int xTesto = (Settings.FRAME_WIDTH - fm.stringWidth(currentScorePlayer) - 150) / 2;
        int yTesto = (Settings.FRAME_HEIGHT - fm.getHeight())/ 2 + fm.getAscent();

        g2d.setColor(Color.BLACK);
        g2d.drawString(currentScorePlayer, xTesto, yTesto);
    }

    @Override
    protected void checkPause() {
        if (gameOffline.getPaused()) {
            int choice = JOptionPane.showConfirmDialog(null, "Do you want to resume the game?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.NO_OPTION) {
                GameOffline.getInstance().reset();
                sound.resetGameOST();
            } else if (choice == JOptionPane.YES_OPTION) {
                sound.gameOST();
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
        this.setFocusable(true);
        this.requestFocus();
    }

    public BriscolaController getController() { return this.controller; }

}