package model;

import java.util.ArrayList;
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
    private ArrayList<Integer> completed;

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
                new int[]{1, 1, 1, 0, 0, 4, 4, 1, 3, 3},
                new int[]{2, 2, 1, 0, 0, 4, 4, 2, 3, 3},
                new int[]{1, 2, 0, 5, 2, 2, 2, 2, 3, 3}};

        tile = new Tetromino();
        tile.addObserver(this);
        completed = new ArrayList<>();
    }

    public void startGame(){
        tile = new Tetromino();
        tile.addObserver(this);
        preview = new Tetromino();
    }

    public void applyGravity(){
        if(!Game.tetrominoCollides(tile, tile.getX(), tile.getY()+1)) tile.move(0, 1);
        if(Game.tetrominoCollides(tile, tile.getX(), tile.getY()+1)) updateBoard();
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
                System.out.println("in the loop");
                if(tetromino.getRepr()[i][j] != 0){
                    System.out.println("checking coords, x: " + (j+tX) + " y: " + (i+tY));
                    // trying to move out of bounds
                    if(j+tX < 0 || j+tX > 9 || i+tY > 21) {
                        return true;
                    }

                    // colliding
                    if(board[i+tY][j+tX]!=0) return true;
                }
            }
        }
        System.out.println("returning no collision");
        return false;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Tetromino) {
            System.out.println("calling from game update");
            if(arg == GameEvent.BOARD_CHANGE) updateBoard();
            else if(Game.tetrominoCollides(tile, tile.getX(), tile.getY())) updateBoard();
            else {
                setChanged();
                notifyObservers(o);
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
                    board[i+tile.getY()][j+ tile.getX()] = tile.getRepr()[i][j];
                }
            }
        }

        tile = preview;
        tile.addObserver(this);
        applyGravity();
        preview = new Tetromino();
        setChanged();
        notifyObservers(GameEvent.BOARD_CHANGE);
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
        int i = board.length-1;
        while (i>=0){
            if (Arrays.stream(board[i]).noneMatch(num -> num == 0)){
                changed = true;
                clearRows(i);
                addClearedRow(i);
            } else i-=1;
        }

        if(changed){
            setChanged();
            notifyObservers(GameEvent.ROW_COMPLETED);
        }
    }

    public void clearRows(int rowIn) {
        for(int i = rowIn; i > 0; i--) {
            board[i] = board[i-1];
        }
    }

    public void addClearedRow(int r) {
        completed.add(r);
        rowsCleared++;
        if(rowsCleared==10){
            level++;
            setChanged();
            notifyObservers(GameEvent.LEVEL_UP);
            rowsCleared = 0;
        }
    }

    public ArrayList<Integer> getCompleted() {
        ArrayList<Integer> done = (ArrayList<Integer>) completed.clone();
        completed = new ArrayList<>();
        return done;
    }

    public int getLevel() { return level; }

    public Tetromino getTile() { return tile; }

    public int[][] getBoard() { return board; }
}
