package model;

import java.util.Observable;

@SuppressWarnings("deprecation")
public class Tetromino extends Observable {

    public enum Direction {
        RIGHT, LEFT;
    }

    private final TileShape shape;
    private int squareSize;
    private Rotation rotation;
    public static final int TILE_SIZE = 38;
    private int[][] repr;
    private int x,y;

    public Tetromino() {
        this.shape = TileShape.getRandomShape();
        squareSize = shape.getSquareSize();
        this.rotation = Rotation.STANDARD;
        y = 0;
        x = shape.generateSpawnX();
        repr = shape.generateRepr();
    }

    public Tetromino(int x, int y){
        this.shape = TileShape.getRandomShape();
        this.x = x;
        this.y = y;
        repr = shape.generateRepr();
    }

    public Tetromino(int[][] repr, int x, int y, TileShape shape, Rotation rotation){
        this.repr = repr;
        this.shape = shape;
        this.x = x;
        this.y = y;
    }

    public void move(int velX, int velY){
        y+=velY;
        x+=velX;
        System.out.println("moving");
        setChanged();
        notifyObservers(this);
    }

    public boolean tryRotating(Direction dir) {

        int[][] newPosition = repr.clone();

        // transpose
        for (int i = 0; i < newPosition.length; i++) {
            for (int j = i; j < newPosition.length; j++) {
                int temp = newPosition[i][j];
                newPosition[i][j] = newPosition[j][i];
                newPosition[j][i] = temp;
            }
        }

        newPosition = dir==Direction.LEFT ? rotateLeft(newPosition) : rotateRight(newPosition);

        if(!Game.tetrominoCollides(new Tetromino(newPosition, x, y, shape, rotation), x, y)){
            repr = newPosition;
            rotation = rotation.getNext(dir);
            setChanged();
            notifyObservers(this);
            return true;
        }

        return false;
    }

    public int[][] rotateRight(int[][] newPosition){

        for (int i = 0; i < newPosition.length; i++) {
            for (int j = 0; j < newPosition.length / 2; j++) {
                int temp = newPosition[i][j];
                newPosition[i][j] = newPosition[i][newPosition.length - j - 1];
                newPosition[i][newPosition.length - j - 1] = temp;
            }
        }
        return newPosition;
    }

    public int[][] rotateLeft(int[][] newPosition){
        for (int i = 0; i < newPosition.length; i++) {
            for (int j = 0; j < newPosition.length / 2; j++) {
                int temp = newPosition[j][i];
                newPosition[j][i] = newPosition[newPosition.length - j - 1][i];
                newPosition[newPosition.length - j - 1][i] = temp;
            }
        }
        return newPosition;
    }

    public int[][] getRepr() { return repr; }

    public TileShape getShape() { return shape; }

    public int getSquareSize() { return squareSize; }

    public Rotation getRotation() { return rotation; }

    public int getX(){ return x; }

    public int getY() { return y; }
}
