package Model;

import Controller.SquareController;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Matthieu Le Boucher
 */
public class Cracker {
    public int x;

    public int y;

    float radius;

    Timer timer;

    double timeSinceExplosion = 0;

    private int remainingTicks;

    private boolean hasExploded = false;

    Square square;

    public Cracker(int x, int y, float radius, int delay, int ticksBeforeExplosion, Square square) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.square = square;
        this.remainingTicks = ticksBeforeExplosion;

        this.timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Tick, remaining ticks: " + remainingTicks);

                if(remainingTicks == 0) {
                    timer.cancel();
                    explode();
                }

                remainingTicks--;
            }
        }, delay, 1_000);
    }

    private void explode() {
        remainingTicks = SquareController.CRACKER_THREAT_TIME;
        hasExploded = true;

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(remainingTicks == 0) {
                    timer.cancel();
                    removeSelf();
                }

                remainingTicks--;
            }
        }, 0, 1_000);
    }

    private void removeSelf() {
        square.removeCracker(this);
    }

    private boolean hasExploded() {
        return hasExploded;
    }
}
