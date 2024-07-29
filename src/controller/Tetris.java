package controller;

import model.Game;
import view.TetrisFrame;

public class Tetris {

    public static long NS_WAIT = 1000000000/60;
    private static double dropTime;
    private static boolean playing;

    public static void main(String[] args) {
        long timeBefore;
        long timeAfter;
        long timeDifference;
        long nsSleep;

        TetrisFrame frame = TetrisFrame.getInstance();

        Controller controller = Controller.getInstance();

        // starting drop time
        dropTime = 1;

        long timeStart = System.nanoTime();

        while(!playing){
            try{
                Thread.sleep(1);
            }catch(InterruptedException e){e.printStackTrace();}
        }

        while (playing){
            timeBefore = System.nanoTime();

            if (System.nanoTime() - timeStart >= dropTime){
                controller.game.applyGravity();
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

    public static void setPlaying(boolean p) { playing = p; }

}
