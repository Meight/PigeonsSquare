package Model;

import Controller.SquareController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Matthieu Le Boucher
 */
public class Cracker implements Drawable {
    public int x;

    public int y;

    private float radius;

    private Timer timer;

    private int remainingTicks;

    private float explosionPercentage;

    private boolean hasExploded = false;

    private Square square;

    private int fps = 20;

    private int explosionTicks = SquareController.CRACKER_THREAT_TIME * fps;

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

                if(remainingTicks == 1) {
                    timer.cancel();
                    explode();
                }

                remainingTicks--;
            }
        }, delay, 1_000);
    }

    private void explode() {
        remainingTicks = explosionTicks;
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
                System.out.println("Remaining ticks: " + remainingTicks);
            }
        }, 0, 1_000 / fps);
    }

    private void removeSelf() {
        square.removeCracker(this);
    }

    public boolean hasExploded() {
        return hasExploded;
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        if(!hasExploded) {
            graphicsContext.setFill(Color.RED);
            graphicsContext.setLineWidth(2);
            graphicsContext.fillRect(x - 10, y - 10, 20, 20);
            graphicsContext.fillText(String.valueOf(remainingTicks), x - 5, y + 25);
        } else {
            graphicsContext.setLineWidth(2);
            graphicsContext.setFill(Color.RED.deriveColor(0, 1, 1,
                    0.8 * ((float) remainingTicks) / explosionTicks));
            graphicsContext.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        }
    }

    public float getRadius() {
        return radius;
    }
}
