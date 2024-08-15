package view;

import controller.Controller;
import controller.Tetris;
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

    public static final Dimension FRAME_SIZE = new Dimension(Tetromino.TILE_SIZE*10, Tetromino.TILE_SIZE*20+19);

    private static TetrisFrame instance;
    protected JPanel panelShower;
    protected CardLayout panelSelection;

    private Game game;

    private GamePanel gamePanel;
    private MenuPanel menuPanel;
    private PausePanel pausePanel;

    private TetrisFrame(){
        setSize(FRAME_SIZE);
        panelShower = new JPanel();
        panelSelection = new CardLayout();
        panelShower.setLayout(panelSelection);

        Controller.getInstance().addObserver(this);

        menuPanel = new MenuPanel();
        panelShower.add(menuPanel, Screen.MENU.name());
        panelSelection.show(panelShower, Screen.MENU.name());

        pausePanel = new PausePanel();
        panelShower.add(pausePanel, Screen.PAUSE.name());

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
        switch (arg) {
            // at game start
            case Game newGame -> {
                game = newGame;

                gamePanel = new GamePanel(game.getTile(), game.getBoard());

                panelShower.add(gamePanel, Screen.GAME.name());
                panelSelection.show(panelShower, Screen.GAME.name());
            }

            case Game.GameEvent event -> {

                switch (event){

                    case END -> {
                        switchScreen(Screen.END);
                    }

                    case LEVEL_UP -> {

                    }

                    case BOARD_CHANGE -> {
                        gamePanel.setBoard(game.getBoard());
                        gamePanel.setTile(game.getTile());
                        gamePanel.repaint();
                    }

                    case GAME -> gamePanel.repaint();

                    case ROW_COMPLETED -> {
                        gamePanel.getAnimationPanel().rowCleared(game.getCompleted());
                        gamePanel.setBoard(game.getBoard());
                        gamePanel.setTile(game.getTile());
                        gamePanel.repaint();
                    }
                }

            }

            case Tetromino tile -> {
                gamePanel.setTile(tile);
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

    public void pauseClicked() {
        Tetris.setPaused(true);
        switchScreen(Screen.PAUSE);
    }

    public void endPauseClicked() {
        Tetris.setPaused(false);
        switchScreen(Screen.GAME);
        gamePanel.requestFocusInWindow();
    }
}
