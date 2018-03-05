package Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


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

    /**
     * This square is an observer. Needs to be noticed whenever the food has been eaten.
     */
    private Square square;

    public Food(int x, int y, double timeFresh, Square square) {
        this.x = x;
        this.y = y;
        this.timeFresh = timeFresh;
        this.square = square;
    }

    public void rotten(double time) {
        if(isFresh && time < timeFresh)
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
        graphicsContext.setFill(isFresh ? Color.GREEN : Color.RED);
        graphicsContext.strokeOval(x, y, 7, 7);
    }
}
