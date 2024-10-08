package controller;

import view.TetrisFrame;

public class Tetris {

    public static long NS_WAIT = 1000000000/60;
    private static double dropTime;
    private static boolean playing;
    private static boolean paused;

    public static void main(String[] args) {
        long timeBefore;
        long timeAfter;
        long timeDifference;
        long nsSleep;

        TetrisFrame.getInstance();
        Controller controller = Controller.getInstance();

        // starting drop time
        dropTime = 1000000000;

        long timeStart = System.nanoTime();

        while(!playing){
            try{
                Thread.sleep(1);
            }catch(InterruptedException e){e.printStackTrace();}
        }

        while(playing) {

            while(paused) {
                try{
                    Thread.sleep(1);
                } catch(InterruptedException e) { e.printStackTrace(); }
            }

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

    public static void setPaused(boolean p) { paused = p; }

}
