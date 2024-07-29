package model;

public enum TileShape {

    //Cyan I, Yellow O, Purple T, Green S, Red Z, Blue J, Orange L

    O(1), I(2), S(3), Z(4),
    L(5), J(6), T(7);

    private int color;

    TileShape(int color){
        this.color = color;
    }

    public int getColor() { return color; }
    
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

}
