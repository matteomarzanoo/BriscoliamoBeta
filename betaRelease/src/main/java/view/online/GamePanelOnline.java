package view.online;

import controller.online.BriscolaControllerOnline;
import model.*;
import model.online.GameOnline;
import org.example.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Objects;

public class GamePanelOnline extends JPanel {
    private String statusString = "Waiting for another player...";
    private EndPanelOnline endPanelOnline;
    private BriscolaControllerOnline controller;
    private GameOnline gameOnline;
    private Player player;
    private boolean giocato;

    public GamePanelOnline() {
        this.gameOnline = GameOnline.getInstance();
        this.player = gameOnline.getPlayer();
        setController(controller);
        setVisible(true);
        setFocusable(true);
        updatePlayerCardPositions();
    }

    private void draw(Graphics2D g2d) {
        try {
            drawSecondPlayerHand(g2d);
            drawBriscolaAndDeck(g2d);
            drawCardsOnTheGround(g2d);
            drawPlayerHand(g2d);
            drawScores(g2d);
            updatePlayerCardPositions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawScores(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        if (!gameOnline.isGameOnlineOver()) {
            g2d.setFont(Fonts.getGamePanel());

            // System.out.println(gameOnline.getScoresPlayer());

            if (!gameOnline.getNicknamesPlayer().isEmpty() && gameOnline.getNicknamesPlayer().size() >= 2)
            {
                g2d.drawString(gameOnline.getNicknamesPlayer().getFirst() + " : " + gameOnline.getScoresPlayer().getFirst(), 565, 450);
                g2d.drawString(gameOnline.getNicknamesPlayer().getLast() + " : " + gameOnline.getScoresPlayer().getLast(), 565, 480);
            }

            if (gameOnline.getSizeDeck() > 0) {
                g2d.drawString(String.valueOf(gameOnline.getSizeDeck()), 700, 360);
            }
        }
    }

    private void drawSecondPlayerHand(Graphics2D g2d) {
        for (int i = 0; i < gameOnline.getLengthHandOpponent(); i++) {
            Image cardImage = loadCardImage("/carte/Dorso3.png");
            g2d.drawImage(cardImage, 150 + 125 * i, 25, Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
        }
    }

    public void updatePlayerCardPositions() {
        int yPosition = Settings.FRAME_HEIGHT - Settings.CARD_HEIGHT - 60;

        for (int i = 0; i < player.getHandPlayer().size(); i++) {
            Card card = player.getHandPlayer().get(i);
            int xPosition = 150 + 125 * i;
            card.setX(xPosition);
            card.setY(yPosition);
        }
    }

    private void drawPlayerHand(Graphics2D g2d) {
        if (player.getHandPlayer().isEmpty() && !giocato) {
            for (int i = 0; i < player.getHandPlayer().size(); i++) {
                Image cardImage = loadCardImage("/carte/Dorso3.png");
                g2d.drawImage(cardImage, 150 + 125 * i, 395, Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
            }
        } else {
            for (int i = 0; i < player.getHandPlayer().size(); i++) {
                Card card = player.getHandPlayer().get(i);
                Image cardImage = loadCardImage(card.getCardPath());

                if (card.isOver()) {
                    int newX = card.getX() - 8;
                    int newY = card.getY() - 10;
                    int newCardWidth = (int) (Settings.CARD_WIDTH * 1.15);
                    int newCardHeight = (int) (Settings.CARD_HEIGHT * 1.15);
                    g2d.drawImage(cardImage, newX, newY, newCardWidth, newCardHeight, null);
                } else if (card.getX() < 0 && card.getY() < 0) {
                    g2d.drawImage(cardImage, 0, 0, Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
                } else if (card.getX() > Settings.FRAME_WIDTH - Settings.CARD_WIDTH - 15 && card.getY() < 0) {
                    g2d.drawImage(cardImage, Settings.FRAME_WIDTH - Settings.CARD_WIDTH - 15, 0, Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
                } else if (card.getX() < 0 && card.getY() > Settings.FRAME_HEIGHT - Settings.CARD_HEIGHT - 35) {
                    g2d.drawImage(cardImage, 0, Settings.FRAME_HEIGHT - Settings.CARD_HEIGHT - 35, Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
                } else if (card.getX() > Settings.FRAME_WIDTH - Settings.CARD_WIDTH - 15 && card.getY() > Settings.FRAME_HEIGHT - Settings.CARD_HEIGHT - 35) {
                    g2d.drawImage(cardImage, Settings.FRAME_WIDTH - Settings.CARD_WIDTH - 15, Settings.FRAME_HEIGHT - Settings.CARD_HEIGHT - 35, Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
                } else if (card.getY() < 0) {
                    g2d.drawImage(cardImage, card.getX(), 0, Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
                } else if (card.getX() < 0) {
                    g2d.drawImage(cardImage, 0, card.getY(), Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
                } else if (card.getX() > Settings.FRAME_WIDTH - Settings.CARD_WIDTH - 15) {
                    g2d.drawImage(cardImage, Settings.FRAME_WIDTH - Settings.CARD_WIDTH - 15, card.getY(), Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
                } else if (card.getY() > Settings.FRAME_HEIGHT - Settings.CARD_HEIGHT - 35) {
                    g2d.drawImage(cardImage, card.getX(), Settings.FRAME_HEIGHT - Settings.CARD_HEIGHT - 35, Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
                } else {
                    g2d.drawImage(cardImage, card.getX(), card.getY(), Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
                }
            }
            giocato = true;
        }
    }

    private void drawBriscolaAndDeck(Graphics2D g) {
        if (gameOnline.getBriscola() != null && gameOnline.getSizeDeck() != 0) {
            Image bri = loadCardImage(gameOnline.getBriscola().getCardPath());
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.rotate(Math.toRadians(270), (int) (Settings.CARD_WIDTH / 2), (int) (Settings.CARD_HEIGHT / 2));
            g2d.drawImage(bri, -180, 580, Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
            g2d.dispose();

            Image dorsoBriscola = loadCardImage("/carte/Dorso3.png");
            g.drawImage(dorsoBriscola, 650, 180, Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
        }
    }

    private void drawCardsOnTheGround(Graphics g) {
        if (!gameOnline.getCardsOnTheGround().isEmpty()) {
            for (int i = 0; i < gameOnline.getCardsOnTheGround().size(); i++) {
                Card card = gameOnline.getCardsOnTheGround().get(i);
                Image cardImage = loadCardImage(card.getCardPath());
                g.drawImage(cardImage, 200 + 150 * i, 204, Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
            }
        }
    }

    private void drawArea(Graphics2D g2d) {
        int x = Settings.PLAYING_AREA_LEFT_BORDER;
        int y = Settings.PLAYING_AREA_UP_BORDER;

        int width = Settings.PLAYING_AREA_RIGHT_BORDER - Settings.PLAYING_AREA_LEFT_BORDER;
        int height = Settings.PLAYING_AREA_DOWN_BORDER - Settings.PLAYING_AREA_UP_BORDER;
        g2d.setColor(Color.white);
        g2d.drawRect(x, y, width, height);
    }

    private Image loadCardImage(String path) {
        try {
            return new ImageIcon(Objects.requireNonNull(getClass().getResource(path))).getImage();
        } catch (Exception e) {
            System.err.println("Failed to load image: " + path);
            return null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = getGraphics2D((Graphics2D) g);

        g2d.setColor(Color.WHITE);
        g2d.setFont(Fonts.getGamePanel());
        if(gameOnline.getLengthHandOpponent() == -1)
        {
            g2d.drawString(statusString, 270, Settings.FRAME_HEIGHT / 2);
        }
        else
        {
            statusString = "";
        }


        if (gameOnline.getCardsOnTheGround().size() == 2) {
            repaint();
        }

        if (gameOnline.isGameOnlineOver())
        {

            this.endPanelOnline = new EndPanelOnline();
            endPanelOnline.setBounds(220, 160, Settings.END_WIDTH, Settings.END_HEIGHT);
            this.add(endPanelOnline);
            GameOffline.getInstance().reset();
            repaint();
        }
        else if (gameOnline.getLengthHandOpponent() != -1 )
        {
            if(gameOnline.isMyTurn())
            {
                g2d.drawString("Your turn", 565, 40);
            }
            else
            {
                g2d.drawString("Wait for your turn", 565, 40);
            }
        }
        draw(g2d);
    }

    private static Graphics2D getGraphics2D(Graphics2D g) {
        Color color1 = new Color(0x00, 0x64, 0x00);
        Color color2 = new Color(0x32, 0xCD, 0x32);

        Point2D center = new Point2D.Float(Settings.FRAME_WIDTH / 2.0f - 80, Settings.FRAME_HEIGHT / 2.0f);
        float radius = Math.min(Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT) / 2.0f;

        RadialGradientPaint paint = new RadialGradientPaint(center, radius, new float[]{0f, 1f}, new Color[]{color2, color1});

        g.setPaint(paint);
        g.fillRect(0, 0, Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT);
        return g;
    }

    public void setController(BriscolaControllerOnline controller) {
        this.controller = controller;
        addKeyListener(controller);
        requestFocusInWindow();
    }
}
