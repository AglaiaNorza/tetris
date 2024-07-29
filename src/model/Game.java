package model;

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

    public static final int HOR_VEL = 48;
    public static final int GRAVITY = 48;

    public Game() {
        this.state = GameEvent.INIT;
        this.score = 0;
        board = new int[20][10];
    }

    public void startGame(){
        tile = new Tetromino();
        preview = new Tetromino(200, 46);
    }

    public void applyGravity(){
        tile.move(0, GRAVITY);
    }

    /**
     * Used to check whether a Tetromino collides with the rest of the board.
     * To collide, it just needs to hit one solid block.
     * @param tetromino the tile
     * @param x the starting x coordinate of the area that needs to be checked
     * @param y the starting y coordinate of the area that needs to be checked
     */
    public static boolean tetrominoCollides(Tetromino tetromino, int x, int y){

        // avoid going out of bounds
        int tX = Math.max(x,0);
        int tY = Math.max(y, 0);

        for(int i = tY; i < Tetromino.SQUARE_SIZE; i++ ){
            for(int j = tX; j < Tetromino.SQUARE_SIZE; j++ ){
                if(tetromino.getRepr()[i- tY][j-tX] != 0){
                    if(board[i][j]!=0) return true;
                }
            }
        }
        return false;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Tetromino) {
            if(Game.tetrominoCollides(tile, tile.getX(), tile.getY())) updateBoard();
        }

    }

    /**
     * Called when a Tetromino has landed. Adds the new Tetromino to the board.
     */
    public void updateBoard(){
        int tY = Math.max(tile.getY(), 0);
        int tX = Math.max(tile.getX(),0);

        for(int i = tY; i < Tetromino.SQUARE_SIZE; i++ ){
            for(int j = tX; j < Tetromino.SQUARE_SIZE; j++ ){
                board[i][j] = tile.getRepr()[i-tY][j-tX];
            }
        }
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
        for (int i= board.length-1; i>=0; i--){
            if (Arrays.stream(board[i]).noneMatch(num -> num == 0)){
                Arrays.fill(board[i], 100); // marks them for deletion
                addClearedRow();
            }
        }
        setChanged();
        notifyObservers(GameEvent.ROW_COMPLETED);
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
}
