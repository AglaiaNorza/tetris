package controller;

import model.Game;
import view.TetrisFrame;

public class Tetris {

    public static long NS_WAIT = 1000000000/60;
    private static double dropTime;

    public static void main(String[] args) {
        long timeBefore;
        long timeAfter;
        long timeDifference;
        long nsSleep;

        boolean playing = true;

        Game game = new Game();
        Controller controller = Controller.getInstance();
        TetrisFrame frame = TetrisFrame.getInstance();

        // starting drop time
        dropTime = 1;

        long timeStart = System.nanoTime();

        // create view and wait for the play button
        game.startGame();

        while (playing){
            timeBefore = System.nanoTime();

            if (System.nanoTime() - timeStart >= dropTime){
                game.applyGravity();
                timeStart = System.nanoTime();
            }
            timeAfter = System.nanoTime();
            timeDifference = timeAfter-timeBefore;
            nsSleep = NS_WAIT - timeDifference;

            try{
                Thread.sleep(Math.max(nsSleep/100000, 0));
            } catch(InterruptedException e) { e.printStackTrace(); }
        }
    }

    public static void setDropTime(double newTime) { dropTime = newTime; }

}
