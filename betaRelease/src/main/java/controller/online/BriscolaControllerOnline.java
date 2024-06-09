package controller.online;

import model.Card;
import model.online.GameOnline;
import model.online.Client;
import view.online.GamePanelOnline;

import java.awt.event.*;
import javax.swing.JOptionPane;

public class BriscolaControllerOnline extends KeyAdapter
{
    private GameOnline game;
    private Client client;
    private GamePanelOnline gamePanel;
    private MouseMotionListener  mouseMotionListener;
    private MouseListener mouseListener;
    private Card tmp;
    private Card draggedCard = null;
    private int dragOffsetX, dragOffsetY;
    private int draggedCardIndex = -1;

    public BriscolaControllerOnline(GamePanelOnline gamePanel)
    {
        this.gamePanel = gamePanel;
        this.game = GameOnline.getInstance();
        this.client = Client.getInstance();

        this.mouseListener = (new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (int i = 0; i < game.getHandPlayer().size(); i++) {
                    Card card = game.getHandPlayer().get(i);
//                    System.out.println("le coordinate del mouse sono: "+ e.getX() + ", "+ e.getY()+ "quelle della carta sono "+ card.getX() + ", "+ card.getY());

                    if (card.contains(e.getPoint())) {
                        //System.out.println("Il mouse si trova sopra la carta "+ i);

                        draggedCard = card;
                        draggedCardIndex = i;

                        dragOffsetX = e.getX() - card.getX();
                        dragOffsetY = e.getY() - card.getY();
                        //System.out.println("Sto premendo la carta " + i +" e la posizione x della carta è: " + card.getX() + " e la posizione y è: "+ card.getY());
                        break;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Imposta le nuove coordinate della carta dopo aver trascinato
                draggedCard.setX(e.getX() - dragOffsetX);
                draggedCard.setY(e.getY() - dragOffsetY);
                if (draggedCard.inPlayArea()){
                    game.clearCardsOnTheGround();
                    playCard(draggedCardIndex);
                }else {
                    gamePanel.updatePlayerCardPositions();
                }

                // Resetta le variabili di trascinamento
                draggedCard = null;
                dragOffsetX = 0;
                dragOffsetY = 0;
                draggedCardIndex = -1;
                gamePanel.repaint();
            }
        });

        this.mouseMotionListener = (new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (draggedCard != null) {
                    draggedCard.setX(e.getX() - dragOffsetX);
                    draggedCard.setY(e.getY() - dragOffsetY);

                    gamePanel.repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {}
        });
        gamePanel.addMouseListener(this.mouseListener);
        gamePanel.addMouseMotionListener(this.mouseMotionListener);
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        game.getCardsOnTheGround().clear();

        if (e.getKeyCode() == KeyEvent.VK_Q)
        {
            int choice = JOptionPane.showConfirmDialog(null, "Do you want to close Briscoliamo?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) { System.exit(0); }
            else { if (choice == JOptionPane.NO_OPTION) {  } }
        }

        if (game.isMyTurn())
        {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_1 ->
                {
                    playCard(0);
                }
                case KeyEvent.VK_2 ->
                {
                    playCard(1);
                }
                case KeyEvent.VK_3 ->
                {
                    playCard(2);
                }
            }
        }
    }

    private void playCard(int index)
    {
        if (game.getHandPlayer().size() > index)
        {
            tmp = game.playedCardPlayer(index);
            gamePanel.updatePlayerCardPositions();

            client.sendMessageToServer(tmp.toString());
            game.getHandPlayer().remove(tmp);
            gamePanel.repaint();
        }
    }
}
