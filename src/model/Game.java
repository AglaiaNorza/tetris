package model;

import controller.Controller;

import javax.crypto.spec.PSource;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class Game extends Observable implements Observer {

    public enum GameEvent {
        INIT, GAME, PAUSE, END, ROW_COMPLETED, LEVEL_UP, BOARD_CHANGE;
    }

    private GameEvent state;
    private int score;
    private static int[][] board;
    private Tetromino tile;
    private Tetromino preview;
    private int level;

    private int rowsCleared;

    public static final int HOR_VEL = 38;
    public static final int GRAVITY = 38;

    public Game() {
        this.state = GameEvent.INIT;
        this.score = 0;
        //board = new int[22][10];
        board = new int[][] {
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 4, 4, 0, 3, 3},
                new int[]{0, 0, 0, 0, 0, 4, 4, 0, 3, 3},
                new int[]{1, 0, 0, 5, 2, 2, 2, 2, 3, 3}};

        tile = new Tetromino();
        tile.addObserver(this);
    }

    public void startGame(){
        tile = new Tetromino();
        tile.addObserver(this);
        preview = new Tetromino();
    }

    public void applyGravity(){
        System.out.println("gravity");
        if(!Game.tetrominoCollides(tile, tile.getX(), tile.getY()+1)) tile.move(0, 1);
        else updateBoard();
    }

    /**
     * Used to check whether a Tetromino collides with the rest of the board.
     * To collide, it just needs to hit one solid block.
     * @param tetromino the tile
     * @param tX the starting x coordinate of the area that needs to be checked
     * @param tY the starting y coordinate of the area that needs to be checked
     */
    public static boolean tetrominoCollides(Tetromino tetromino, int tX, int tY){

        for(int i = 0; i < tetromino.getSquareSize(); i++ ){
            for(int j = 0; j < tetromino.getSquareSize(); j++ ){
                if(tetromino.getRepr()[i][j] != 0){
                    // trying to move out of bounds
                    if(j+tX < 0 || j+tX > 9 || i+tY > 21) return true;
                    // colliding
                    if(board[i+tY][j+tX]!=0) return true;
                }
            }
        }
        return false;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof  Tetromino) {
            if(Game.tetrominoCollides(tile, tile.getX(), tile.getY())) updateBoard();
            else {
                setChanged();
                notifyObservers(arg);
            }
        }
    }

    /**
     * Called when a Tetromino has landed. Adds the new Tetromino to the board.
     */
    public void updateBoard(){
        for(int i = 0; i < tile.getSquareSize(); i++ ){
            for(int j = 0; j < tile.getSquareSize(); j++ ){
                if(tile.getRepr()[i][j] != 0){
                    //System.out.println("changing tiles: " +(i+tile.getY()) + " "+(j+tile.getX()));
                    board[i+tile.getY()][j+ tile.getX()] = tile.getRepr()[i][j];
                }
            }
        }
        tile = preview;
        tile.addObserver(this);
        applyGravity();
        preview = new Tetromino();
        handleRows();

        if(!hasChanged()) {
            setChanged();
            notifyObservers(GameEvent.BOARD_CHANGE);
        }
    }

    /**
     * Handles the completion of the rows
     */
    public void handleRows(){
        boolean changed = false;

        for (int i= board.length-1; i>=0; i--){
            if (Arrays.stream(board[i]).noneMatch(num -> num == 0)){
                System.out.println("change");
                changed = true;
                Arrays.fill(board[i], 5); // marks them for deletion
                addClearedRow();
            }
        }

        if(changed){
            setChanged();
            notifyObservers(GameEvent.ROW_COMPLETED);
        }
    }

    public void addClearedRow() {
        rowsCleared++;
        if(rowsCleared==10){
            level++;
            setChanged();
            notifyObservers(GameEvent.LEVEL_UP);
            rowsCleared = 0;
        }
    }

    public int getLevel() { return level; }

    public Tetromino getTile() { return tile; }

    public int[][] getBoard() { return board; }
}
