package view.online;

import controller.online.BriscolaControllerOnline;
import model.*;
import model.online.GameOnline;
import org.example.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Objects;

public class GamePanelOnline extends JPanel
{
    private JLabel labelTurn = new JLabel("WAITING FOR ANOTHER PLAYER...");
    private EndPanelOnline endPanelOnline;
    private BriscolaControllerOnline controller;
    private GameOnline game;
    private Player player;
    private boolean giocato;

    public GamePanelOnline()
    {
        this.game = GameOnline.getInstance();
        this.player = game.getPlayer();
        setController(controller);
        setVisible(true);
        setFocusable(true);
        updatePlayerCardPositions();
        add(labelTurn);
    }

    private void draw(Graphics2D g2d)
    {
        try
        {
            drawSecondPlayerHand(g2d);
            drawBriscolaAndDeck(g2d);
            drawCardsOnTheGround(g2d);
            //drawArea(g2d);
            drawPlayerHand(g2d);
            updatePlayerCardPositions();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void drawSecondPlayerHand(Graphics2D g2d)
    {
        for (int i = 0; i < game.getLengthHandOpponent(); i++)
        {
            Image cardImage = loadCardImage("/carte/Dorso3.png");
            g2d.drawImage(cardImage, 150 + 125 * i, 25, Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
        }
    }
    public void updatePlayerCardPositions() {
        int yPosition = Settings.FRAME_HEIGHT - Settings.CARD_HEIGHT - 60;  // Imposta la posizione Y delle carte nella parte inferiore del pannello con un piccolo margine dal bordo inferiore

        for (int i = 0; i < player.getHandPlayer().size(); i++) {
            Card card = player.getHandPlayer().get(i);
            int xPosition = 150 + 125 * i;  // Calcola la posizione X delle carte
            card.setX(xPosition);  // Aggiorna la posizione X della carta
            card.setY(yPosition);  // Aggiorna la posizione Y della carta
        }
    }

    private void drawPlayerHand(Graphics2D g2d)
    {
        if (player.getHandPlayer().isEmpty() && !giocato)
        {
            for (int i = 0; i < 3; i++)
            {
                Image cardImage = loadCardImage("/carte/Dorso3.png");
                g2d.drawImage(cardImage, 150 + 125 * i, 395, Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
            }
        }
        else
        {
            for (int i = 0; i < player.getHandPlayer().size(); i++)
            {
                Card card = player.getHandPlayer().get(i);
                Image cardImage = loadCardImage(card.getCardPath());
                if (card.isOver()) {
                    int newX = card.getX() - 8;
                    int newY = card.getY() - 10;
                    int newCardWidth = (int) (Settings.CARD_WIDTH * 1.15);
                    int newCardHeight = (int) (Settings.CARD_HEIGHT * 1.15);
                    g2d.drawImage(cardImage, newX, newY, newCardWidth, newCardHeight, null);
                }
                //Gestisco tutti i casi in cui le carte si trovano sui bordi del frame per non poterle portare fuori dal pannello
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
            giocato = true;
        }
    }

    private void drawBriscolaAndDeck(Graphics2D g)
    {
        if(game.getBriscola() != null && game.getSizeDeck() != 0)
        {
            Image bri = loadCardImage(game.getBriscola().getCardPath());
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.rotate(Math.toRadians(270), (int)(Settings.CARD_WIDTH /2), (int) (Settings.CARD_HEIGHT/2));
            g2d.drawImage(bri, -180, 580, Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
            g2d.dispose();

            Image dorsoBriscola = loadCardImage("/carte/Dorso3.png");
            g.drawImage(dorsoBriscola, 650, 180, Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
        }
    }

    private void drawCardsOnTheGround(Graphics g)
    {
        if (!game.getCardsOnTheGround().isEmpty())
        {
            for (int i = 0; i < game.getCardsOnTheGround().size(); i++)
            {
                Card card = game.getCardsOnTheGround().get(i);
                Image cardImage = loadCardImage(card.getCardPath());
                g.drawImage(cardImage, 200 + 150 * i, 204, Settings.CARD_WIDTH, Settings.CARD_HEIGHT, null);
            }
        }
    }

    private void drawArea(Graphics2D g2d){
        int x = Settings.PLAYING_AREA_LEFT_BORDER;
        int y = Settings.PLAYING_AREA_UP_BORDER;

        int width = Settings.PLAYING_AREA_RIGHT_BORDER - Settings.PLAYING_AREA_LEFT_BORDER;
        int height = Settings.PLAYING_AREA_DOWN_BORDER - Settings.PLAYING_AREA_UP_BORDER;
        g2d.drawRect(x, y, width, height);
    }

    private Image loadCardImage(String path)
    {
        try
        {
            return new ImageIcon(Objects.requireNonNull(getClass().getResource(path))).getImage();
        }
        catch (Exception e)
        {
            System.err.println("Failed to load image: " + path);
            return null;
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        //this.updatePlayerCardPositions();

        Graphics2D g2d = getGraphics2D((Graphics2D) g);


        if(game.getCardsOnTheGround().size() == 2)
        {
            repaint();
            System.out.println("aggiorno per le tavolo sul tavolo che adesso sono due");
        }

        if (game.isGameOnlineOver())
        {
            System.out.println("Partita finita!");

            this.endPanelOnline = new EndPanelOnline();
            endPanelOnline.setBounds(220,160, Settings.END_WIDTH, Settings.END_HEIGHT); // Imposta la posizione e dimensione dell'EndPanel
            this.add(endPanelOnline);
            remove(labelTurn); // Hide the label when endPanelOnline is shown
            GameOffline.getInstance().reset();
            repaint();
        }
        else
        {
            if(game.getLengthHandOpponent() != -1)
                updateLabelTurn(game.isMyTurn() ? "YOUR TURN" : "WAIT FOR YOUR TURN");
            labelTurn.setVisible(true); // Ensure the label is visible when endPanelOnline is not shown
        }
        // Disegna lo sfondo
        draw(g2d);
    }

    private static Graphics2D getGraphics2D(Graphics2D g) {
        Color color1 =  new Color(0x00, 0x64, 0x00); // Colore più scuro
        Color color2 =  new Color(0x32, 0xCD, 0x32); // Colore più chiaro

        Point2D center = new Point2D.Float(Settings.FRAME_WIDTH / 2.0f - 80, Settings.FRAME_HEIGHT/ 2.0f);
        float radius = Math.min(Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT)/2.0f;

        RadialGradientPaint paint = new RadialGradientPaint(center, radius, new float[]{0f, 1f}, new Color[]{color2, color1});

        g.setPaint(paint);
        g.fillRect(0, 0, Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT);
        return g;
    }

    public void setController(BriscolaControllerOnline controller)
    {
        this.controller = controller;
        addKeyListener(controller);
        requestFocusInWindow();
    }

    public void updateLabelTurn(String message)
    {
        this.labelTurn.setText(message);
    }
}
