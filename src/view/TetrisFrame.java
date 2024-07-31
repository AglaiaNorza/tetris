package view;

import controller.Controller;
import model.Game;
import model.Tetromino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class TetrisFrame extends JFrame implements Observer {

    public enum Screen {
        MENU, GAME, PAUSE, HELP, SETTINGS, END;
    }

    private static TetrisFrame instance;
    protected JPanel panelShower;
    protected CardLayout panelSelection;

    private Game game;

    private GamePanel gamePanel;
    private MenuPanel menuPanel;

    private TetrisFrame(){
        setSize(new Dimension(Tetromino.TILE_SIZE*10, Tetromino.TILE_SIZE*20+19));
        panelShower = new JPanel();
        panelSelection = new CardLayout();
        panelShower.setLayout(panelSelection);

        Controller.getInstance().addObserver(this);

        menuPanel = new MenuPanel();
        panelShower.add(menuPanel, Screen.MENU.name());
        panelSelection.show(panelShower, Screen.MENU.name());

        add(panelShower);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static TetrisFrame getInstance(){
        if(instance == null) instance = new TetrisFrame();
        return instance;
    }

    public void switchScreen(Screen screen) {
        panelSelection.show(panelShower, screen.name());
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("arg is" + arg.getClass());
        switch (arg) {
            // at game start
            case Game newGame -> {
                game = newGame;

                gamePanel = new GamePanel(game.getTile(), game.getBoard());

                panelShower.add(gamePanel, Screen.GAME.name());
                panelSelection.show(panelShower, Screen.GAME.name());
            }

            case Game.GameEvent event -> {

                System.out.println("here");

                switch (event){

                    case END -> {
                        switchScreen(Screen.END);
                    }

                    case LEVEL_UP -> {

                    }

                    case BOARD_CHANGE, ROW_COMPLETED -> {
                        gamePanel.setBoard(game.getBoard());
                        gamePanel.setTile(game.getTile());
                        gamePanel.repaint();
                    }
                }

            }

            case Tetromino tile -> {
                gamePanel.setTile((Tetromino)arg);
                gamePanel.repaint();
            }

            default -> {}
        }
    }

    @Override
    public synchronized void addKeyListener(KeyListener l) {
        gamePanel.addKeyListener(l);
    }

    public void gameStartClicked() {
        Controller.getInstance().startGame();
        gamePanel.requestFocusInWindow();
    }
}
