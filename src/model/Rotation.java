package model;

public enum Rotation {
    STANDARD, RIGHT, UPSIDE_DOWN, LEFT;

    public Rotation getNext(Tetromino.Direction dir){
        return switch (this){
            case STANDARD -> dir == Tetromino.Direction.RIGHT ? RIGHT : LEFT;
            case RIGHT -> dir == Tetromino.Direction.RIGHT ? UPSIDE_DOWN : STANDARD;
            case UPSIDE_DOWN -> dir == Tetromino.Direction.RIGHT ? LEFT : RIGHT;
            case LEFT -> dir == Tetromino.Direction.RIGHT ? STANDARD : UPSIDE_DOWN;
        };
    }
}