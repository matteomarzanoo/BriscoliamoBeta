package controller;

import model.GameOffline;
import model.Sound;
import model.Card;
import view.GamePanelOffline;
import view.MenuPanel;

import java.awt.event.*;
import javax.swing.Timer;
import javax.swing.JOptionPane;

public class BriscolaController extends KeyAdapter
{
    private GameOffline game;
    private GamePanelOffline gamePanelOffline;
    private MouseMotionListener  mouseMotionListener;
    private MouseListener mouseListener;
    private Card tmp, tmpBot;
    private Card draggedCard = null;
    private int dragOffsetX, dragOffsetY;
    private int draggedCardIndex = -1;

    public BriscolaController(GamePanelOffline gamePanelOffline) {
        this.gamePanelOffline = gamePanelOffline;
        this.game = GameOffline.getInstance();

        this.mouseListener = (new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(game.isMyTurn()){
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
                }else{
                    System.out.println("Controller:Non puoi giocare, non e' il tuo turno");
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {

                try {
                    draggedCard.setX(e.getX() - dragOffsetX);
                    draggedCard.setY(e.getY() - dragOffsetY);
                    if (draggedCard.inPlayArea()) {
                        playCard(draggedCardIndex);
                    } else {
                        gamePanelOffline.updatePlayerCardPositions();
                    }
                    draggedCard = null;
                    dragOffsetX = 0;
                    dragOffsetY = 0;
                    draggedCardIndex = -1;
                    gamePanelOffline.repaint();
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
                        gamePanelOffline.repaint();
                    }
                }catch(NullPointerException exception){}
            }



            @Override
            public void mouseMoved(MouseEvent e) {
                for (int i = 0; i<game.getHandPlayer().size(); i++) {
                    Card card = game.getHandPlayer().get(i);
                    if (card.contains(e.getPoint())) {
                        card.setOver(true);
                    } else {
                        card.setOver(false);
                    }
                }
                gamePanelOffline.repaint();
            }
        });
    }


    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            MenuPanel.getInstance().exit();
        }

        if (e.getKeyCode() ==  KeyEvent.VK_B) { 
            new Sound().playClickedButton();
            int choice = JOptionPane.showConfirmDialog(null, "Do you want to go back to the menu?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                        Sound.pause(Sound.gameOST);
                        Sound.restart(Sound.menuOST);
                        MenuPanel.getInstance().home();
                        game.setPaused();
            }
        }

        if (!game.isGameOver()) {
            if(game.isMyTurn()) {
                switch ((e.getKeyCode())) {
                    case KeyEvent.VK_1 -> playCard(0);
                    case KeyEvent.VK_2 -> playCard(1);
                    case KeyEvent.VK_3 -> playCard(2);
                }
            }
        }
    }


    public MouseMotionListener getMouseMotionListener(){return this.mouseMotionListener;}
    public MouseListener getMouseListener(){return this.mouseListener;}
    
    private void playCard(int index) {
        if ((game.getHandPlayer().size() >= index) && game.isMyTurn() && (game.getCardsOnTheGround().size() < 2)) {
            game.playedCardPlayer(index);
            gamePanelOffline.repaint();

            if (game.getCardsOnTheGround().size() == 2) {
                aspetta();
            }
            if (!game.isMyTurn()) {
                playBot();
            }
        }
    }
    private void playBot(){
        while (!game.isMyTurn() && game.getCardsOnTheGround().size() < 2) {
            game.playedCardBot();
            gamePanelOffline.repaint();

            if (game.getCardsOnTheGround().size() == 2) {
                aspetta();
            }
        }
    }

    private void aspetta() {
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                game.attributePoints();
                game.addCard(game.getHandBot());
                game.addCard(game.getHandPlayer());
                game.clearTmpArray();
                gamePanelOffline.updatePlayerCardPositions();
                gamePanelOffline.repaint();
                if (!game.isMyTurn()) {
                    playBot();
                }
            }
        });

        timer.setRepeats(false); // Imposta il timer per non ripetersi
        timer.start();
    }

}