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
        MENU, GAME, PAUSE, RULES, THEMES, LEADERBOARD, END;
    }

    public static final Dimension FRAME_SIZE = new Dimension(1140, 760);

    private static TetrisFrame instance;
    protected JPanel panelShower;
    protected CardLayout panelSelection;

    private Game game;

    private BoardPanel boardPanel;
    private GamePanel gamePanel;
    private MenuPanel menuPanel;
    private PausePanel pausePanel;
    private RulesPanel rulesPanel;
    private LeaderboardPanel leaderboardPanel;
    private ThemePanel themePanel;

    private TetrisFrame(){
        setSize(FRAME_SIZE);
        panelShower = new JPanel();
        panelSelection = new CardLayout();
        panelShower.setLayout(panelSelection);
        panelShower.setSize(FRAME_SIZE);

        Controller.getInstance().addObserver(this);

        menuPanel = new MenuPanel();
        panelShower.add(menuPanel, Screen.MENU.name());
        panelSelection.show(panelShower, Screen.MENU.name());

        pausePanel = new PausePanel();
        panelShower.add(pausePanel, Screen.PAUSE.name());

        rulesPanel = new RulesPanel();
        panelShower.add(rulesPanel, Screen.RULES.name());

        leaderboardPanel = new LeaderboardPanel();
        panelShower.add(leaderboardPanel, Screen.LEADERBOARD.name());

        themePanel = new ThemePanel();
        panelShower.add(themePanel, Screen.THEMES.name());

        add(panelShower);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static TetrisFrame getInstance(){
        if(instance == null) instance = new TetrisFrame();
        return instance;
    }

    public void switchScreen(Screen screen) { panelSelection.show(panelShower, screen.name()); }

    @Override
    public void update(Observable o, Object arg) {
        switch (arg) {
            // at game start
            case Game newGame -> {
                game = newGame;

                boardPanel = new BoardPanel(game.getTile(), game.getBoard());
                gamePanel = new GamePanel(boardPanel);

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
                        boardPanel.setBoard(game.getBoard());
                        boardPanel.setTile(game.getTile());
                        boardPanel.repaint();
                    }

                    case GAME -> boardPanel.repaint();

                    case ROW_COMPLETED -> {
                        boardPanel.getAnimationPanel().rowCleared(game.getCompleted());
                        boardPanel.setBoard(game.getBoard());
                        boardPanel.setTile(game.getTile());
                        boardPanel.repaint();
                    }
                }

            }

            case Tetromino tile -> {
                boardPanel.setTile(tile);
                boardPanel.repaint();
            }

            default -> {}
        }
    }

    public void onThemeChange() {
        panelShower.remove(menuPanel);
        menuPanel = new MenuPanel();
        panelShower.add(menuPanel, Screen.MENU.name());
    }

    @Override
    public synchronized void addKeyListener(KeyListener l) {
        boardPanel.addKeyListener(l);
    }

    public void gameStartClicked() {
        Controller.getInstance().startGame();
        boardPanel.requestFocusInWindow();
    }

    public void pauseClicked() {
        Tetris.setPaused(true);
        switchScreen(Screen.PAUSE);
    }

    public void endPauseClicked() {
        Tetris.setPaused(false);
        switchScreen(Screen.GAME);
        boardPanel.requestFocusInWindow();
    }
}
