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
    private TetrisFrame frame;
    private KeyHandler keyH;

    private Tetromino tile;

    private Controller(){
        keyH = new KeyHandler();
    }

    public static Controller getInstance(){
        if(instance==null) instance = new Controller();
        return instance;
    }

    public void startGame(){
        game = new Game();
        game.addObserver(this);
        setChanged();
        notifyObservers(game);
        Tetris.setPlaying(true);
        game.startGame();
        TetrisFrame.getInstance().addKeyListener(keyH);
    }

    public void pauseGame(){
    }

    public void endGame(){
    }

    public void handleMovement() {
        System.out.println("checking");
        System.out.println("upPressed: " + keyH.isUpPressed() + " upR: " + keyH.isUpReleased());

        if(keyH.isDownPressed()) {
            if (!Game.tetrominoCollides(tile, tile.getX(), tile.getY() + 2)) {
                tile.move(0, 1);
                tile.move(0, 1);
            }
        }

        if (keyH.isLeftPressed()) {
            if(!Game.tetrominoCollides(tile, tile.getX()-1, tile.getY()))
                tile.move(-1, 0);
        }
        else if (keyH.isRightPressed()) {
            if(!Game.tetrominoCollides(tile, tile.getX()+1, tile.getY()))
                    tile.move(1, 0);
        }

        else if((keyH.isUpPressed() && keyH.isUpReleased()) || (keyH.isXPressed() && keyH.isXReleased())){
            tile.tryRotating(Tetromino.Direction.RIGHT);
        }

        else if((keyH.isControlPressed() && keyH.isControlReleased()) || (keyH.isZPressed() && keyH.isZReleased())){
            tile.tryRotating(Tetromino.Direction.LEFT);
        }

        else if(keyH.isSpacePressed() && keyH.isSpaceReleased()){
            //hard drop
        }
        setChanged();
        notifyObservers(tile);
    }

    //called when the game does stuff (ex end of game)
    @Override
    public void update(Observable o, Object arg) {
        switch (arg){

            case Tetromino tetromino -> {
                System.out.println("received move by "+ tetromino.getShape());
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
