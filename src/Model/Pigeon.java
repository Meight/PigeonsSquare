package Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by lefou on 20/02/2018.
 * @author Matthieu Le Boucher
 */
public abstract class Pigeon extends Thread implements Drawable {
    /**
     * The x coordinate of the pigeon on the screen.
     */
    public int x;

    /**
     * The y coordinate of the pigeon on the screen.
     */
    public int y;

    /**
     * The movement speed of the pigeon.
     */
    private double speed;

    /**
     * The square within which the pigeon is living.
     */
    protected Square square;

    /**
     * The food being currently targeted by the pigeon.
     */
    protected Food targetFood;

    public Pigeon(int x, int y, double speed, Square square) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.square = square;
    }

    protected void refreshTargetFood() {
        targetFood = square.getClosestFood(x, y);
    }

    /**
     * The basic behavior of any pigeon, regardless of its species.
     * @param dt The time elapsed since last loop.
     */
    protected void animate(double dt) {
        if(targetFood == null)
            refreshTargetFood();
        else {
            // If the pigeon is targeting food, make it move in its direction.
            double distanceToFood = Math.hypot(targetFood.x - x, targetFood.y - y);
            double foodDirectionX = (targetFood.x - x) / distanceToFood; // Normalized.
            double foodDirectionY = (targetFood.y - y) / distanceToFood; // Normalized.

            x += foodDirectionX * speed * dt;
            y += foodDirectionY * speed * dt;


        }
    }

    /**
     * @return The color of the individual, based on its species.
     */
    public abstract Color getColor();

    /**
     * @return The size of the individual, based on its species.
     */
    public abstract int getSize();

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(getColor());
        graphicsContext.setLineWidth(2);
        graphicsContext.fillOval(x, y, getSize(), getSize());
    }
}