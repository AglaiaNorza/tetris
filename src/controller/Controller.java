package controller;

import model.Game;
import model.Tetromino;
import view.TetrisFrame;

import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class Controller extends Observable implements Observer {

    private static Controller instance;

    protected Game game;
    private KeyHandler keyH;

    private Tetromino tile;

    private Controller() {
        keyH = new KeyHandler();
    }

    public static Controller getInstance() {
        if(instance==null) instance = new Controller();
        return instance;
    }

    public void startGame() {
        game = new Game();
        game.addObserver(this);
        setChanged();
        notifyObservers(game);
        Tetris.setPlaying(true);
        game.startGame();
        TetrisFrame.getInstance().addKeyListener(keyH);
    }

    public void endGame() {
    }

    public void handleMovement() {

        if(keyH.isDownPressed() && keyH.isDownReleased()) {
            //move twice as fast
            game.applyGravity();
            game.applyGravity();
        }
        if (keyH.isLeftPressed() && keyH.isLeftReleased()) {
            if(!Game.tetrominoCollides(tile, tile.getX()-1, tile.getY())) tile.move(-1, 0);
        }
        else if (keyH.isRightPressed() && keyH.isRightReleased()) {
            if(!Game.tetrominoCollides(tile, tile.getX()+1, tile.getY())) tile.move(1, 0);
        }
        else if((keyH.isUpPressed() && keyH.isUpReleased()) || (keyH.isXPressed() && keyH.isXReleased())){
            tile.tryRotating(Tetromino.Direction.RIGHT);
        }
        else if((keyH.isControlPressed() && keyH.isControlReleased()) || (keyH.isZPressed() && keyH.isZReleased())){
            tile.tryRotating(Tetromino.Direction.LEFT);
        }
        else if(keyH.isSpacePressed() && keyH.isSpaceReleased()) tile.hardDrop();

        setChanged();
        notifyObservers(tile);
    }

    //called when the game does stuff (ex end of game)
    @Override
    public void update(Observable o, Object arg) {
        switch (arg){

            case Tetromino tetromino -> {
                this.tile = tetromino;
                setChanged();
                notifyObservers(tetromino);
            }

            case Game.GameEvent event -> {
                switch (event){
                    case LEVEL_UP -> {
                        Tetris.setDropTime(Math.pow((0.8-((game.getLevel()-1)*0.007)),(game.getLevel()-1)));
                        setChanged();
                        notifyObservers(event);
                    }

                    case ROW_COMPLETED, BOARD_CHANGE -> {
                        keyH.onLand();
                        setTetromino(game.getTile());
                        setChanged();
                        notifyObservers(event);
                    }
                }

            }

            default -> {}

        }
    }

    public void setTetromino(Tetromino tile) { this.tile = tile; }
}
