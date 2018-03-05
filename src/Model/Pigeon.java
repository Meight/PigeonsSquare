package Model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static Controller.SquareController.OPTIMAL_FRAME_TIME;

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

    protected boolean simulationRunning = false;
    private double lastFpsTime = 0;

    private int fps = 0;

    public Pigeon(int x, int y, double speed, Square square) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.square = square;
    }

    protected void refreshTargetFood() {
        targetFood = square.getClosestFood(x, y);
    }

    public void simulate() {
        long lastLoopTime = System.nanoTime();
        System.out.println("Launching simulation for pigeon " + this);

        while (simulationRunning) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double) OPTIMAL_FRAME_TIME);

            // Update the frame counter.
            lastFpsTime += updateLength;
            fps++;

            if (lastFpsTime >= 1_000_000_000) {
                lastFpsTime = 0;
                fps = 0;
            }

            animate(delta);

            try {
                Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_FRAME_TIME) / 1_000_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The basic behavior of any pigeon, regardless of its species.
     * @param dt The time elapsed since last loop.
     */
    protected void animate(double dt) {
        if(targetFood == null || !targetFood.isFresh() || targetFood.hasBeenEaten()) {
            refreshTargetFood();
        } else {
            // If the pigeon is targeting food, make it move in its direction.
            double distanceToFood = Math.hypot(targetFood.x - x, targetFood.y - y);
            double foodDirectionX = (targetFood.x - x) / distanceToFood; // Normalized.
            double foodDirectionY = (targetFood.y - y) / distanceToFood; // Normalized.

            System.out.println(this + " moving in direction (" + foodDirectionX + ", " + foodDirectionY + ")" +
                    "x += " + (foodDirectionX * speed * dt) + ", y += " + (foodDirectionY * speed * dt));

            x += foodDirectionX * speed * dt;
            y += foodDirectionY * speed * dt;

            // Refresh distance to target food.
            distanceToFood = Math.hypot(targetFood.x - x, targetFood.y - y);

            if(distanceToFood < getSize() && targetFood.isFresh()) {
                // Target food can be reached by the pigeon. Eat it.
                targetFood.eat();

                // No matter whether or not the pigeon could eat the food first, it will have too look
                // for other food next turn.
                targetFood = null;
            }
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