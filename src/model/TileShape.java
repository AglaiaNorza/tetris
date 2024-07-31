package model;

public enum TileShape {

    //Cyan I, Yellow O, Purple T, Green S, Red Z, Blue J, Orange L

    O(1, 4), I(2, 4), S(3, 3), Z(4, 3),
    L(5, 3), J(6, 3), T(7, 3);

    private int color;
    private int squareSize;

    TileShape(int color, int squareSize){
        this.color = color;
        this.squareSize = squareSize;
    }

    public int getColor() { return color; }

    public int getSquareSize() { return squareSize; }
    
    public static TileShape getRandomShape(){
        return switch ((int)(Math.random() * 6)){
            case 0 -> O;
            case 1 -> I;
            case 2 -> S;
            case 3 -> Z;
            case 4 -> L;
            case 5 -> J;
            default -> T;
        };
    }

    public int[][] generateRepr(){
        return switch (this){
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

    public int generateSpawnX(){
        return switch (this){
            case I, O -> Math.random()<0.5 ? 3 : 4;
            default -> Math.random()<0.5 ? 1 : 2;
        };
    }

}
