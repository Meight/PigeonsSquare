package Model.Bird;

import Model.Cracker;
import Model.Drawable;
import Model.Food;
import Model.Square;
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

    private boolean simulationRunning = false;
    private double lastFpsTime = 0;

    private int fps = 0;

    Pigeon(int x, int y, double speed, Square square) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.square = square;
    }

    private void refreshTargetFood() {
        targetFood = square.getClosestFood(x, y);
    }

    /**
     * Triggered whenever some food is spawned on the square.
     * @param newFood The food that just got spawned on the square.
     */
    public void onFoodSpawn(Food newFood) {
        if(targetFood == null) {
            targetFood = newFood;
        } else if(newFood.isFresh()) {
            if(Math.hypot(newFood.x - x, newFood.y - y) < Math.hypot(targetFood.x - x, targetFood.y - y)) {
                targetFood = newFood;
            }
        }
    }

    private void simulate() {
        long lastLoopTime = System.nanoTime();

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
                Thread.sleep(Math.max((lastLoopTime - System.nanoTime() + OPTIMAL_FRAME_TIME) / 1_000_000, 1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The basic behavior of any pigeon, regardless of its species.
     * @param dt The time elapsed since last loop.
     */
    private void animate(double dt) {
        // Scan the square for crackers.
        if(!square.getCrackers().isEmpty()) {
            for (Cracker cracker : square.getCrackers()) {
                if(cracker.hasExploded() && Math.hypot(cracker.x - x, cracker.y - y) < cracker.getRadius())
                    moveOppositeOf(cracker.x, cracker.y, dt);
            }
        } else {
            // If there's no cracker, look for food.
            if (targetFood == null || !targetFood.isFresh() || targetFood.hasBeenEaten()) {
                refreshTargetFood();
            } else {
                // If the pigeon is targeting food, make it move in its direction.
                moveTowards(targetFood.x, targetFood.y, dt);

                // Refresh distance to target food.
                double distanceToFood = Math.hypot(targetFood.x - x, targetFood.y - y);

                if (distanceToFood < getSize() && targetFood.isFresh()) {
                    // Target food can be reached by the pigeon. Eat it.
                    targetFood.eat();

                    // No matter whether or not the pigeon could eat the food first, it will have too look
                    // for other food next turn.
                    targetFood = null;
                }
            }
        }
    }

    private void moveTowards(int targetX, int targetY, double dt) {
        moveTowards(targetX, targetY, dt, false);
    }

    private void moveOppositeOf(int targetX, int targetY, double dt) {
        moveTowards(targetX, targetY, dt, true);
    }

    private void moveTowards(int targetX, int targetY, double dt, boolean oppositeDirection) {
        int wayFactor = oppositeDirection ? -1 : 1;
        double distanceToTarget = Math.hypot(targetX - x, targetY - y);
        double targetDirectionX = wayFactor * (targetX - x) / distanceToTarget; // Normalized.
        double targetDirectionY = wayFactor * (targetY - y) / distanceToTarget; // Normalized.

        x += targetDirectionX * speed * dt;
        y += targetDirectionY * speed * dt;
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

    @Override
    public void run() {
        simulationRunning = true;
        simulate();
    }
}