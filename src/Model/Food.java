package Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Timer;
import java.util.TimerTask;


/**
 * @author Matthieu Le Boucher
 */
public class Food implements Drawable {
    /**
     * The x coordinate of the food's position.
     */
    public int x;

    /**
     * The y coordinate of the food's position.
     */
    public int y;

    /**
     * The absolute time after which this food will turn bad.
     */
    private double timeFresh;

    private boolean isFresh = true;

    private boolean hasBeenEaten = false;

    private Timer timer;

    private int remainingTicks;

    /**
     * This square is an observer. Needs to be noticed whenever the food has been eaten.
     */
    private Square square;

    public Food(int x, int y, int timeFresh, Square square) {
        this.x = x;
        this.y = y;

        this.square = square;

        this.remainingTicks = timeFresh;

        this.timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(remainingTicks == 1) {
                    timer.cancel();
                    rotten();
                }

                remainingTicks--;
            }
        }, 0, 1_000);
    }

    private void rotten() {
        isFresh = false;
    }

    public boolean isFresh() {
        return isFresh;
    }

    public boolean hasBeenEaten() {
        return hasBeenEaten;
    }

    public synchronized void eat() {
        if(square.getFoods().contains(this)) {
            // Notify the square that this food doesn't exist anymore.
            square.removeFood(this);
            hasBeenEaten = true;
        }
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setStroke(isFresh ? Color.GREEN : Color.RED);
        graphicsContext.strokeOval(x, y, 10, 10);
    }
}
