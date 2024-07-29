package model;

import java.util.Observable;

@SuppressWarnings("deprecation")
public class Tetromino extends Observable {

    public enum Direction {
        RIGHT, LEFT;
    }

    public static final int SQUARE_SIZE = 4;

    private final TileShape shape;
    private int color;
    public static final int TILE_SIZE = 48;
    private int[][] repr;
    private int x,y;
    //private int velX, velY;

    // todo
    //   Tetromino start locations
    //
    //    The I and O spawn in the middle columns
    //    The rest spawn in the left-middle columns
    //    The tetriminoes spawn horizontally with J, L and T spawning flat-side first.
    //    Spawn above playfield, row 21 for I, and 21/22 for all other tetriminoes.
    //    Immediately drop one space if no existing Block is in its path

    public Tetromino() {
        this.shape = TileShape.getRandomShape();
        this.color = (int)(Math.random() * 6);
        y = 0;
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
                    {0,0,0,0},
                    {0,0,color,color},
                    {0,0,color,color}
            };
            case I -> new int[][]{
                    {0,0,0,color},
                    {0,0,0,color},
                    {0,0,0,color},
                    {0,0,0,color}
            };
            case S -> new int[][]{
                    {0,0,0,0},
                    {0,0,0,0},
                    {0,0,color,color},
                    {0,color,color,0}
            };
            case Z -> new int[][]{
                    {0,0,0,0},
                    {0,0,0,0},
                    {0,color,color,0},
                    {0,0,color,color}
            };
            case L -> new int[][]{
                    {0,0,0,0},
                    {0,0,color,0},
                    {0,0,color,0},
                    {0,0,color,0},
                    {0,0,color,color}
            };
            case J -> new int[][]{
                    {0,0,0,color},
                    {0,0,0,color},
                    {0,0,0,color},
                    {0,0,0,color},
                    {0,0,color,color}
            };

            case T -> new int[][]{
                    {0,0,0,0},
                    {0,0,0,0},
                    {0,color,color,color},
                    {0,0,color,0}
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
        return  newPosition;
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

    public int getX(){ return x; }

    public int getY() { return y; }
}
