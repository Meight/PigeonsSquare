package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthieu Le Boucher
 * A square is an abstraction of the viewport.
 */
public class Square {
    /**
     * Width in pixels of the viewport.
     */
    private int width;

    /**
     * Height in pixels of the viewport.
     */
    private int height;

    /**
     * List of the pigeons currently living in the square.
     */
    private List<Pigeon> pigeons;

    /**
     * List of all the food that was dropped in the square.
     */
    private List<Food> foods;

    public Square(int width, int height) {
        this.width = width;
        this.height = height;
        pigeons = new ArrayList<>();
        foods = new ArrayList<>();
    }

    /**
     * Find the closest food to a given point (x, y) within the square.
     * @param x The x coordinate of the point.
     * @param y THe y coordinate of the point.
     * @return The closest food to (x, y) if it exists, null otherwise.
     */
    public Food getClosestFood(int x, int y) {
        double minimalDistance = Float.POSITIVE_INFINITY;
        Food closestFood = null;

        for(Food food : foods) {
            if(food.isFresh() && !food.hasBeenEaten()) {
                double distance = Math.hypot(food.x - x, food.y - y);

                if (distance < minimalDistance) {
                    minimalDistance = distance;
                    closestFood = food;
                }
            }
        }

        return closestFood;
    }

    /**
     * Starts the threads underlying behind the Pigeon abstraction.
     */
    public void animatePigeons() {
        for(Pigeon pigeon : pigeons) {
            pigeon.start();
        }
    }

    public void addPigeon(Pigeon pigeon) {
        this.pigeons.add(pigeon);
    }

    public void addFood(Food food) {
        this.foods.add(food);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Pigeon> getPigeons() {
        return pigeons;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void removeFood(Food food) {
        foods.remove(food);
    }
}
