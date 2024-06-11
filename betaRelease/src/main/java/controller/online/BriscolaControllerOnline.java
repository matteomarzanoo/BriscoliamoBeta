package controller.online;

import model.Card;
import model.Sound;
import model.online.GameOnline;
import model.scores.OnlineScore;
import model.online.Client;
import view.MenuPanel;
import view.online.GamePanelOnline;

import java.awt.event.*;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
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
    private MenuPanel menuPanel;
    private Sound sound;
    private OnlineScore onlineScore;

    public BriscolaControllerOnline(GamePanelOnline gamePanel)
    {
        this.gamePanel = gamePanel;
        this.game = GameOnline.getInstance();
        this.client = Client.getInstance();
        this.menuPanel = MenuPanel.getInstance();
        this.sound = Sound.getInstance();
        this.onlineScore = OnlineScore.getInstance();

        this.mouseListener = (new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (int i = 0; i < game.getHandPlayer().size(); i++) {
                    Card card = game.getHandPlayer().get(i);
                    if (card.contains(e.getPoint())) {
                        draggedCard = card;
                        draggedCardIndex = i;

                        dragOffsetX = e.getX() - card.getX();
                        dragOffsetY = e.getY() - card.getY();
                        break;
                    }
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(game.isMyTurn()) {
                    for (int i = 0; i < game.getHandPlayer().size(); i++) {
                        Card card = game.getHandPlayer().get(i);
                        if (card.contains(e.getPoint())) {
                            playCard(i);
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    draggedCard.setX(e.getX() - dragOffsetX);
                    draggedCard.setY(e.getY() - dragOffsetY);
                    if (draggedCard.inPlayArea() && game.isMyTurn()) {
                        playCard(draggedCardIndex);
                    } else {
                        gamePanel.updatePlayerCardPositions();
                    }
                    draggedCard = null;
                    dragOffsetX = 0;
                    dragOffsetY = 0;
                    draggedCardIndex = -1;
                    gamePanel.repaint();
                }catch (NullPointerException exception){}
            }
        });

        this.mouseMotionListener = (new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                try {
                    if (draggedCard != null) {
                        draggedCard.setOver(false);

                        draggedCard.setX(e.getX() - dragOffsetX);
                        draggedCard.setY(e.getY() - dragOffsetY);
                        gamePanel.repaint();
                    }
                }catch(NullPointerException exception){}
            }

            @Override
            public void mouseMoved(MouseEvent e)  {
                    for (int i = 0; i<game.getHandPlayer().size(); i++) {
                        Card card = game.getHandPlayer().get(i);
                        card.setOver(card.contains(e.getPoint()));
                    }
                    gamePanel.repaint();
                }

        });
        gamePanel.addMouseListener(this.mouseListener);
        gamePanel.addMouseMotionListener(this.mouseMotionListener);
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        game.getCardsOnTheGround().clear();

        if (e.getKeyCode() == KeyEvent.VK_Q) {
            menuPanel.getMenuState().exit();
        }

        if (e.getKeyCode() ==  KeyEvent.VK_B) { 
            try {
                sound.sfx("hitted_button.wav");
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {}
            int choice = JOptionPane.showConfirmDialog(null, "Do you want to go back to the menu? The match will be cancelled and you will LOSE.", "Confirm", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                onlineScore.addLost();
                game.reset();
                sound.pauseGameOST();
                menuPanel.getMenuState().home();
                sound.resetMenuOST();
            }
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
            try {
                sound.sfx("played_card.wav");
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {}

            client.sendMessageToServer(tmp.toString());
            game.getHandPlayer().remove(tmp);
            gamePanel.repaint();
        }
    }
}
