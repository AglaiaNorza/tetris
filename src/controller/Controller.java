package controller;

import model.Game;
import model.Tetromino;
import view.TetrisFrame;

import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class Controller extends Observable implements Observer {

    private static Controller instance;

    private Game game;
    private TetrisFrame frame;
    private KeyHandler keyH;

    private Tetromino tile;

    private Controller(){
        game = new Game();
        frame = TetrisFrame.getInstance();

        game.addObserver(this);
        addObserver(frame);

        keyH = new KeyHandler();
    }

    public static Controller getInstance(){
        if(instance==null) instance = new Controller();
        return instance;
    }

    public void startGame(){
        game.startGame();
    }

    public void pauseGame(){
    }

    public void endGame(){
    }


    public void handleMovement() {

        if(keyH.isDownPressed()){
            tile.move(0, Game.GRAVITY *2);
        }

        if (keyH.isLeftPressed()) {
            if(!Game.tetrominoCollides(tile, tile.getX()-Game.HOR_VEL, tile.getY())){
                tile.move(-Game.HOR_VEL, Game.GRAVITY);
            }
        }
        else if (keyH.isRightPressed()) {
            if(!Game.tetrominoCollides(tile, tile.getX()+Game.HOR_VEL, tile.getY())){
                tile.move(+Game.HOR_VEL, Game.GRAVITY);
            }
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
        if (o instanceof Game){
            if(arg == Game.GameEvent.LEVEL_UP){
                Tetris.setDropTime(Math.pow((0.8-((game.getLevel()-1)*0.007)),(game.getLevel()-1)));
                setChanged();
                notifyObservers(Game.GameEvent.LEVEL_UP);
            }

        }
    }

    public void setTetromino(Tetromino tile) { this.tile = tile; }
}
