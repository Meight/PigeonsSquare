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

    private float radius;

    private Timer timer;

    private int remainingTicks;

    private boolean hasExploded = false;

    private Square square;

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
        remainingTicks = SquareController.CRACKER_THREAT_TIME + 1;
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

    public boolean hasExploded() {
        return hasExploded;
    }
}
