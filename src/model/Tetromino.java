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
    private int color;
    public static final int TILE_SIZE = 38;
    private int[][] repr;
    private int x,y;

    // todo
    //   Tetromino start locations
    //    The I and O spawn in the middle columns
    //    The rest spawn in the left-middle columns
    //    Spawn above playfield, row 21 for I, and 21/22 for all other tetriminoes.

    public Tetromino() {
        this.shape = TileShape.getRandomShape();
        squareSize = shape.getSquareSize();
        this.color = (int)(Math.random() * 6);
        this.rotation = Rotation.STANDARD;
        y = -3;
        x = (int) (Math.random()*(7+3)-3);//random spawn x
        repr = generateRepr();
    }

    public Tetromino(int x, int y){
        this.shape = TileShape.getRandomShape();
        this.color = (int)(Math.random()*6);
        this.x = x;
        this.y = y;
        repr = generateRepr();
    }

    public Tetromino(int[][] repr, int x, int y, TileShape shape){
        this.repr = repr;
        this.shape = shape;
        this.x = x;
        this.y = y;
    }

    public void move(int velX, int velY){
        System.out.println("moving"+velX);
        y+=velY;
        x+=velX;
        setChanged();
        notifyObservers(this);
    }

    public int[][] generateRepr(){
        return switch (shape){
            case O -> new int[][]{
                    {0,0,0,0},
                    {0,color,color,0},
                    {0,color,color,0},
                    {0,0,0,0}
            };
            case I -> new int[][]{
                    {0,0,0,0},
                    {color,color,color,color},
                    {0,0,0,0},
                    {0,0,0,0}
            };
            case S -> new int[][]{
                    {0,color,color},
                    {color,color,0},
                    {0,0,0,}
            };
            case Z -> new int[][]{
                    {color,color,0,0},
                    {0,color,color},
                    {0,0,0}
            };
            case L -> new int[][]{
                    {0,0,color},
                    {color,color,color},
                    {0,0,0}
            };
            case J -> new int[][]{
                    {color,0,0},
                    {color,color,color},
                    {0,0,0,0}
            };

            case T -> new int[][]{
                    {0,color,0},
                    {color,color, color},
                    {0,0,0}
            };
        };
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

        if(!Game.tetrominoCollides(new Tetromino(newPosition, x, y, shape), x, y)){
            repr = newPosition;
            rotation = rotation.getNext(dir);
            setChanged();
            notifyObservers();
            return true;
        }

        return false;
    }

    public int[][] rotateLeft(int[][] newPosition){

        for (int i = 0; i < newPosition.length; i++) {
            for (int j = 0; j < newPosition.length / 2; j++) {
                int temp = newPosition[i][j];
                newPosition[i][j] = newPosition[i][newPosition.length - j - 1];
                newPosition[i][newPosition.length - j - 1] = temp;
            }
        }
        return newPosition;
    }

    public int[][] rotateRight(int[][] newPosition){
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
